package com.ctm.eadvogado;

import java.io.IOException;
import java.net.URLEncoder;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ctm.eadvogado.endpoints.deviceEndpoint.DeviceEndpoint;
import com.ctm.eadvogado.util.EndpointUtils;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

/**
 * This class is started up as a service of the Android application. It listens
 * for Google Cloud Messaging (GCM) messages directed to this device.
 * 
 * When the device is successfully registered for GCM, a mensagem is sent to the
 * App Engine backend via Cloud Endpoints, indicating that it wants to receive
 * broadcast messages from the it.
 * 
 * Before registering for GCM, you have to create a project in Google's Cloud
 * Console (https://code.google.com/apis/console). In this project, you'll have
 * to enable the "Google Cloud Messaging for Android" Service.
 * 
 * Once you have set up a project and enabled GCM, you'll have to set the
 * PROJECT_NUMBER field to the project number mentioned in the "Overview" page.
 * 
 * See the documentation at
 * http://developers.google.com/eclipse/docs/cloud_endpoints for more
 * information.
 */
public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "e-Advogado";

	private final DeviceEndpoint endpoint;

	/*
	 * TODO: Set this to a valid project number. See
	 * http://developers.google.com/eclipse/docs/cloud_endpoint for more
	 * information.
	 */
	protected static final String PROJECT_NUMBER = "935757146253";

	/**
	 * Register the device for GCM.
	 * 
	 * @param mContext
	 *            the activity's context.
	 */
	public static void register(Context mContext) {
		GCMRegistrar.checkDevice(mContext);
		GCMRegistrar.checkManifest(mContext);
		GCMRegistrar.register(mContext, PROJECT_NUMBER);
	}

	/**
	 * Unregister the device from the GCM service.
	 * 
	 * @param mContext
	 *            the activity's context.
	 */
	public static void unregister(Context mContext) {
		GCMRegistrar.unregister(mContext);
	}

	public GCMIntentService() {
		super(PROJECT_NUMBER);
		endpoint = EndpointUtils.initDeviceEndpoint();
	}

	/**
	 * Called on registration error. This is called in the context of a Service
	 * - no dialog or UI.
	 * 
	 * @param context
	 *            the Context
	 * @param errorId
	 *            an error mensagem
	 */
	@Override
	public void onError(Context context, String errorId) {
		Log.e(TAG, "Erro ao registrar device no servico GCM! " + errorId);
	}

	/**
	 * Called when a cloud mensagem has been received.
	 */
	@Override
	public void onMessage(Context context, Intent messageIntent) {
		Bundle bundle = messageIntent.getExtras();
		String tipoNotificacao = bundle.getString("tipoNotificacao");
		
		Intent notificationIntent = null;
		if (tipoNotificacao.equals("movimentacao")) {
			notificationIntent = new Intent(context, ProcessoTabsPagerFragment.class);
			notificationIntent.putExtra("gcmIntentServiceMessage", true);
			notificationIntent.putExtra("npu", bundle.getString("npu"));
			notificationIntent.putExtra("idTribunal", bundle.getString("idTribunal"));
			notificationIntent.putExtra("tipoJuizo", bundle.getString("tipoJuizo"));
		} else {
			notificationIntent = new Intent(context, MainActivity.class);
			notificationIntent.putExtra("gcmIntentServiceMessage", true);
		}
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(notificationIntent);
		} else {
			doNotification(context, notificationIntent, bundle);
		}
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void doNotification(Context context, Intent notificationIntent, Bundle bundle) {
		String titulo = bundle.getString("titulo");
		String mensagem = bundle.getString("mensagem");
		
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

		// Build notification
		// Actions are just fake
		Builder builder = new Notification.Builder(context)
				.setContentTitle(titulo).setContentText(mensagem)
				.setSmallIcon(R.drawable.ic_content_read)
				.setContentIntent(pIntent);
		Notification noti = builder.build();

		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		// Hide the notification after its selected
		noti.flags |= Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify(0, noti);
	}
	

	/**
	 * Called back when a registration token has been received from the Google
	 * Cloud Messaging service.
	 * 
	 * @param context
	 *            the Context
	 */
	@Override
	public void onRegistered(Context context, String registrationId) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		String email = preferences.getString(PreferencesActivity.PREFS_KEY_EMAIL, "");
		String senha = preferences.getString(PreferencesActivity.PREFS_KEY_SENHA, "");
		
		int tries = 3;
		int attempt = 0;
		boolean isRegistered = false;
		while (attempt < tries && !isRegistered) {
			try {
				endpoint.registrarDispositivo(email, senha, registrationId,
						URLEncoder.encode(android.os.Build.MANUFACTURER + " "
								+ android.os.Build.PRODUCT, "UTF-8")).execute();
				isRegistered = true;
			} catch(GoogleJsonResponseException e) {
				Log.e(TAG, "Falha ao registrar device no endpoint!", e);
			} catch (IOException e) {
				Log.e(TAG, "Erro de comunicação ao tentar registrar device no endpoint!", e);
				break;
			}
			attempt++;
		}
	}

	/**
	 * Called back when the Google Cloud Messaging service has unregistered the
	 * device.
	 * 
	 * @param context
	 *            the Context
	 */
	@Override
	protected void onUnregistered(Context context, String registrationId) {
		if (registrationId != null && registrationId.length() > 0) {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			String email = preferences.getString(PreferencesActivity.PREFS_KEY_EMAIL, "");
			String senha = preferences.getString(PreferencesActivity.PREFS_KEY_SENHA, "");
			
			int tries = 3;
			int attempt = 0;
			boolean isDesregistered = false;
			
			while (attempt < tries && !isDesregistered) {
				try {
					endpoint.desregistrarDispositivo(email, senha, registrationId).execute();
					isDesregistered = true;
				} catch(GoogleJsonResponseException e) {
					Log.e(TAG, "Falha ao desregistrar device no endpoint!", e);
				} catch (IOException e) {
					Log.e(TAG, "Erro de comunicação ao tentar desregistrar device no endpoint!", e);
					break;
				}
				attempt++;
			}
			
		}

	}

}
