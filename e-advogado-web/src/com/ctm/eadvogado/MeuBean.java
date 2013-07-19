/**
 * 
 */
package com.ctm.eadvogado;

import java.io.IOException;

import javax.inject.Named;

import com.ctm.eadvogado.androidpublisher.Utils;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.model.SubscriptionPurchase;

/**
 * @author ctm
 *
 */
@Named
public class MeuBean {
	
	/**
	 * 
	 */
	public MeuBean() {
		// TODO Auto-generated constructor stub
	}
	
	public String getMensagem() {
		
		try {
			AndroidPublisher androidPublisher = Utils.loadAndroidPublisherClient();
			SubscriptionPurchase execute = androidPublisher.purchases().get("com.ctm.eadvogado", "conta_premium", "").execute();
			System.out.println(execute);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Olá Mundo!";
	}

}

