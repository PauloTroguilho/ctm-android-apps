package com.ctm.eadvogado.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ctm.eadvogado.endpoints.processoEndpoint.model.Key;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.Processo;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.TipoPessoa;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.TipoPoloProcessual;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.TipoProcessoJudicial;
import com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal;
import com.ctm.eadvogado.util.ConverterUtil;
import com.ctm.eadvogado.util.JsonUtils;

public class EAdvogadoDbHelper extends SQLiteOpenHelper {
	// If you change the database schema, you must increment the database
	// version.
	public static final int DATABASE_VERSION = 6;
	public static final String DATABASE_NAME = "EAdvogado.db";
	
	protected final Context mHelperContext;

	public EAdvogadoDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mHelperContext = context;
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(EAdvogadoContract.SQL_CREATE_TRIBUNAIS);
		db.execSQL(EAdvogadoContract.SQL_CREATE_PROCESSOS);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy
		// is to simply to discard the data and start over
		
		db.execSQL(EAdvogadoContract.SQL_DELETE_PROCESSOS);
		db.execSQL(EAdvogadoContract.SQL_DELETE_TRIBUNAIS);
		db.execSQL(EAdvogadoContract.SQL_DELETE_LANCAMENTOS);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
    /**
     * Insere um {@link Processo} no banco de dados
     * @param processo
     * @param xmlData
     * @return
     */
    public long inserirProcesso(Processo processo) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(EAdvogadoContract.ProcessoTable.COLUMN_NAME_ID_PROCESSO,
				processo.getKey().getId());
		initialValues.put(EAdvogadoContract.ProcessoTable.COLUMN_NAME_NPU,
				processo.getNpu());
		initialValues.put(EAdvogadoContract.ProcessoTable.COLUMN_NAME_ID_TRIBUNAL,
				processo.getTribunal().getId());
		initialValues.put(EAdvogadoContract.ProcessoTable.COLUMN_NAME_TIPO_JUIZO,
				processo.getTipoJuizo());
		
		String poloAtivo = (String) processo.get(EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_ATIVO);
		if (poloAtivo != null) {
			initialValues.put(EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_ATIVO,
					poloAtivo);
		}
		String poloPassivo = (String) processo.get(EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_PASSIVO);
		if (poloPassivo != null) {
			initialValues.put(EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_PASSIVO,
					poloPassivo);
		}
		TipoProcessoJudicial processoJudicial = processo.getProcessoJudicial();
		if (processoJudicial != null) {
			initialValues.put(EAdvogadoContract.ProcessoTable.COLUMN_NAME_CONTEUDO, 
					processoJudicial.toString());
			if (poloAtivo == null && poloPassivo == null) {
				List<TipoPoloProcessual> ativos = new ArrayList<TipoPoloProcessual>();
				List<TipoPoloProcessual> passivos = new ArrayList<TipoPoloProcessual>();
				ConverterUtil.fillPolosProcessuais(processo.getProcessoJudicial(), ativos, passivos);
				try {
					if (!ativos.isEmpty()) {
						TipoPessoa pessoa = ConverterUtil.getTipoPessoa(ativos.iterator().next());
						poloAtivo = pessoa.getNome().toUpperCase();
						initialValues.put(EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_ATIVO,
								poloAtivo);
					}
				} catch(NullPointerException e) {
					Log.e("e-Advogado", "Erro ao preencher polo ativo no processo: " + processo.getKey().getId());
				}
				try {
					if (!passivos.isEmpty()) {
						TipoPessoa pessoa = ConverterUtil.getTipoPessoa(passivos.iterator().next());
						poloPassivo = pessoa.getNome().toUpperCase();
						initialValues.put(EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_PASSIVO,
								poloPassivo);
					}
				} catch(NullPointerException e) {
					Log.e("e-Advogado", "Erro ao preencher polo passivo no processo: " + processo.getKey().getId());
				}
			}
		}
		
        return getWritableDatabase().insert(EAdvogadoContract.ProcessoTable.TABLE_NAME, null, initialValues);
    }
    
    /**
     * @param processo
     * @return
     */
    public long updateProcesso(Processo processo) {
        ContentValues initialValues = new ContentValues();
		initialValues.put(EAdvogadoContract.ProcessoTable.COLUMN_NAME_NPU,
				processo.getNpu());
		initialValues.put(EAdvogadoContract.ProcessoTable.COLUMN_NAME_ID_TRIBUNAL,
				processo.getTribunal().getId());
		initialValues.put(EAdvogadoContract.ProcessoTable.COLUMN_NAME_TIPO_JUIZO,
				processo.getTipoJuizo());
		TipoProcessoJudicial processoJudicial = processo.getProcessoJudicial();
		if (processoJudicial != null) {
			initialValues.put(EAdvogadoContract.ProcessoTable.COLUMN_NAME_CONTEUDO, 
					processoJudicial.toString());
		}
		String poloAtivo = (String) processo.get(EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_ATIVO);
		if (poloAtivo != null) {
			initialValues.put(EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_ATIVO,
					poloAtivo);
		}
		String poloPassivo = (String) processo.get(EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_PASSIVO);
		if (poloPassivo != null) {
			initialValues.put(EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_PASSIVO,
					poloPassivo);
		}
		
		String whereClause = EAdvogadoContract.ProcessoTable.COLUMN_NAME_ID_PROCESSO + " = ?";
		String[] whereArgs = new String[] {processo.getKey().getId().toString()};

        return getWritableDatabase().update(EAdvogadoContract.ProcessoTable.TABLE_NAME, initialValues, whereClause, 
        		whereArgs);
    }
    
	/**
	 * @param processo
	 * @return
	 */
	public long deleteProcesso(Processo processo) {
		String whereClause = EAdvogadoContract.ProcessoTable.COLUMN_NAME_ID_PROCESSO
				+ " = ?";
		String[] whereArgs = new String[] { processo.getKey().getId()
				.toString() };
		return getWritableDatabase().delete(
				EAdvogadoContract.ProcessoTable.TABLE_NAME, whereClause,
				whereArgs);
	}
    
    /**
     * Insere um {@link Tribunal} no banco de dados
     * @param tribunal
     * @return
     */
    public long inserirTribunal(Tribunal tribunal) {
        ContentValues initialValues = new ContentValues();
		initialValues.put(EAdvogadoContract.TribunalTable.COLUMN_NAME_TRIBUNAL_ID,
				tribunal.getKey().getId());
		initialValues.put(EAdvogadoContract.TribunalTable.COLUMN_NAME_NOME,
				tribunal.getNome());
		initialValues.put(EAdvogadoContract.TribunalTable.COLUMN_NAME_SIGLA,
				tribunal.getSigla());
		initialValues.put(EAdvogadoContract.TribunalTable.COLUMN_NAME_ENDPOINT_1G,
				tribunal.getPje1gEndpoint());
		initialValues.put(EAdvogadoContract.TribunalTable.COLUMN_NAME_ENDPOINT_2G,
				tribunal.getPje2gEndpoint());
		initialValues.put(EAdvogadoContract.TribunalTable.COLUMN_NAME_QTD_CONSULTAS,
				0);

        return getWritableDatabase().insert(EAdvogadoContract.TribunalTable.TABLE_NAME, null, initialValues);
    }
    
    public long updateTribunal(Tribunal tribunal) {
        ContentValues initialValues = new ContentValues();
		initialValues.put(EAdvogadoContract.TribunalTable.COLUMN_NAME_TRIBUNAL_ID,
				tribunal.getKey().getId());
		initialValues.put(EAdvogadoContract.TribunalTable.COLUMN_NAME_NOME,
				tribunal.getNome());
		initialValues.put(EAdvogadoContract.TribunalTable.COLUMN_NAME_SIGLA,
				tribunal.getSigla());
		initialValues.put(EAdvogadoContract.TribunalTable.COLUMN_NAME_ENDPOINT_1G,
				tribunal.getPje1gEndpoint());
		initialValues.put(EAdvogadoContract.TribunalTable.COLUMN_NAME_ENDPOINT_2G,
				tribunal.getPje2gEndpoint());
		Object qtdConsultas = tribunal.get(EAdvogadoContract.TribunalTable.COLUMN_NAME_QTD_CONSULTAS);
		if (qtdConsultas != null) {
			initialValues.put(EAdvogadoContract.TribunalTable.COLUMN_NAME_QTD_CONSULTAS,
					qtdConsultas.toString());
		}
		
		String whereClause = EAdvogadoContract.TribunalTable.COLUMN_NAME_TRIBUNAL_ID + " = ?";
		String[] whereArgs = new String[] {tribunal.getKey().getId().toString()};

        return getWritableDatabase().update(EAdvogadoContract.TribunalTable.TABLE_NAME, initialValues, whereClause, 
        		whereArgs);
    }
    
    /**
     * @param tribunais
     */
    public void inserirTribunais(List<Tribunal> tribunais) {
    	if (tribunais != null && !tribunais.isEmpty()) {
    		for (Tribunal tribunal : tribunais) {
        		Tribunal exTrib = selectTribunalPorId(tribunal.getKey().getId());
        		if (exTrib == null) {
        			inserirTribunal(tribunal);
        		} else {
        			updateTribunal(tribunal);
        		}
    		}
    	}
    }
    
    /**
     * @param processo
     */
    public void insertOrUpdateProcesso(Processo processo) {
    	Processo eProcesso = selectProcesso(processo.getKey().getId(), false);
		if (eProcesso == null) {
			inserirProcesso(processo);
		} else {
			updateProcesso(processo);
		}
    }
    
    /**
     * @param npu
     * @param idTribunal
     * @param tipoJuizo
     * @return
     */
    public Processo selectProcesso(String npu, Long idTribunal, String tipoJuizo, boolean loadJson) {
    	// Define a projection that specifies which columns from the database
    	// you will actually use after this query.
    	String[] projection = {
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_ID_PROCESSO,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_NPU,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_ID_TRIBUNAL,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_TIPO_JUIZO,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_CONTEUDO,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_ATIVO,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_PASSIVO
    	};
    	String selection = EAdvogadoContract.ProcessoTable.COLUMN_NAME_NPU + " = ? AND "
    			 + EAdvogadoContract.ProcessoTable.COLUMN_NAME_ID_TRIBUNAL + " = ? AND "
    			 + EAdvogadoContract.ProcessoTable.COLUMN_NAME_TIPO_JUIZO + " = ?";
    	String[] columnValues = {
    			npu, idTribunal.toString(), tipoJuizo
    	};

    	// How you want the results sorted in the resulting Cursor
    	String sortOrder =
    		EAdvogadoContract.ProcessoTable._ID + " DESC";

    	Cursor c = getReadableDatabase().query(
    			EAdvogadoContract.ProcessoTable.TABLE_NAME,  // The table to query
    	    projection,                               // The columns to return
    	    selection,                                // The columns for the WHERE clause
    	    columnValues,                            // The values for the WHERE clause
    	    null,                                     // don't group the rows
    	    null,                                     // don't filter by row groups
    	    sortOrder                                 // The sort order
    	    );
    	Processo processo = null;
    	if (c.moveToFirst()) {
    		processo = new Processo();
    		processo.setKey(new Key()); 
    		processo.getKey().setId(c.getLong(0));
			processo.setNpu(c.getString(1));
			processo.setTribunal(new Key());
			processo.getTribunal().setId(c.getLong(2));
			processo.setTipoJuizo(c.getString(3));
			if (loadJson) {
				String processoJudicial = c.getString(4);
				if (processoJudicial != null && processoJudicial.length() > 0){
					processo.setProcessoJudicial(JsonUtils.parseQuietly(processoJudicial,
							TipoProcessoJudicial.class));
				}
			}
			processo.set(EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_ATIVO, c.getString(5));
			processo.set(EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_PASSIVO, c.getString(6));
    	}
    	c.close();

        return processo;
    }
    
    public Processo selectProcesso(Long idProcesso, boolean loadJson) {
    	// Define a projection that specifies which columns from the database
    	// you will actually use after this query.
    	String[] projection = {
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_ID_PROCESSO,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_NPU,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_ID_TRIBUNAL,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_TIPO_JUIZO,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_CONTEUDO
    	};
    	String selection = EAdvogadoContract.ProcessoTable.COLUMN_NAME_ID_PROCESSO + " = ?";
    	String[] columnValues = { idProcesso.toString() };

    	// How you want the results sorted in the resulting Cursor
    	String sortOrder =
    		EAdvogadoContract.ProcessoTable._ID + " DESC";

    	Cursor c = getReadableDatabase().query(
    			EAdvogadoContract.ProcessoTable.TABLE_NAME,  // The table to query
    	    projection,                               // The columns to return
    	    selection,                                // The columns for the WHERE clause
    	    columnValues,                            // The values for the WHERE clause
    	    null,                                     // don't group the rows
    	    null,                                     // don't filter by row groups
    	    sortOrder                                 // The sort order
    	    );
    	Processo processo = null;
    	if (c.moveToFirst()) {
    		processo = new Processo();
    		processo.setKey(new Key()); 
    		processo.getKey().setId(c.getLong(0));
			processo.setNpu(c.getString(1));
			processo.setTribunal(new Key());
			processo.getTribunal().setId(c.getLong(2));
			processo.setTipoJuizo(c.getString(3));
			if (loadJson) {
				String processoJudicial = c.getString(4);
				if (processoJudicial != null && processoJudicial.length() > 0){
					processo.setProcessoJudicial(JsonUtils.parseQuietly(processoJudicial,
							TipoProcessoJudicial.class));
				}
			}
    	}
    	c.close();

        return processo;
    }
    
    
    public TipoProcessoJudicial selectProcessoJudicial(Long idProcesso) {
    	// Define a projection that specifies which columns from the database
    	// you will actually use after this query.
    	String[] projection = {
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_CONTEUDO
    	};
    	String selection = EAdvogadoContract.ProcessoTable.COLUMN_NAME_ID_PROCESSO + " = ?";
    	String[] columnValues = { idProcesso.toString() };

    	Cursor c = getReadableDatabase().query(
    			EAdvogadoContract.ProcessoTable.TABLE_NAME,  // The table to query
    	    projection,                               // The columns to return
    	    selection,                                // The columns for the WHERE clause
    	    columnValues,                            // The values for the WHERE clause
    	    null,                                     // don't group the rows
    	    null,                                     // don't filter by row groups
    	    null                                 // The sort order
    	    );
    	TipoProcessoJudicial processo = null;
    	if (c.moveToFirst()) {
    		processo = JsonUtils.parseQuietly(c.getString(0),
					TipoProcessoJudicial.class);
    	}
    	c.close();
        return processo;
    }
    
    public Tribunal selectTribunalPorId(Long id) {
    	// Define a projection that specifies which columns from the database
    	// you will actually use after this query.
    	String[] projection = {
    			EAdvogadoContract.TribunalTable._ID,
    			EAdvogadoContract.TribunalTable.COLUMN_NAME_TRIBUNAL_ID,
    			EAdvogadoContract.TribunalTable.COLUMN_NAME_NOME,
    			EAdvogadoContract.TribunalTable.COLUMN_NAME_SIGLA,
    			EAdvogadoContract.TribunalTable.COLUMN_NAME_ENDPOINT_1G,
    			EAdvogadoContract.TribunalTable.COLUMN_NAME_ENDPOINT_2G,
    			EAdvogadoContract.TribunalTable.COLUMN_NAME_QTD_CONSULTAS
    	};
    	String selection = EAdvogadoContract.TribunalTable.COLUMN_NAME_TRIBUNAL_ID + " = ?";
    	String[] columnValues = {
    			id.toString()
    	};

    	// How you want the results sorted in the resulting Cursor
    	String sortOrder =
    		EAdvogadoContract.TribunalTable.COLUMN_NAME_SIGLA + " ASC";

    	Cursor c = getReadableDatabase().query(
    			EAdvogadoContract.TribunalTable.TABLE_NAME,  // The table to query
    	    projection,                               // The columns to return
    	    selection,                                // The columns for the WHERE clause
    	    columnValues,                            // The values for the WHERE clause
    	    null,                                     // don't group the rows
    	    null,                                     // don't filter by row groups
    	    sortOrder                                 // The sort order
    	    );
    	Tribunal tribunal = null;
    	if (c.moveToFirst()) {
    		tribunal = new Tribunal();
    		tribunal.setKey(new com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Key());
    		tribunal.getKey().setId(c.getLong(1));
			tribunal.setNome(c.getString(2));
			tribunal.setSigla(c.getString(3));
			tribunal.setPje1gEndpoint(c.getString(4));
			tribunal.setPje2gEndpoint(c.getString(5));
			tribunal.set(EAdvogadoContract.TribunalTable.COLUMN_NAME_QTD_CONSULTAS, c.getInt(6));
    	}
    	c.close();

        return tribunal;
    }
    
    public long selectTribunaisCount() {
    	// Define a projection that specifies which columns from the database
    	// you will actually use after this query.
    	String[] projection = {
    			EAdvogadoContract.TribunalTable._ID
    	};

    	Cursor c = getReadableDatabase().query(
    			EAdvogadoContract.TribunalTable.TABLE_NAME,  // The table to query
    	    projection,                               // The columns to return
    	    null,                                // The columns for the WHERE clause
    	    null,                            // The values for the WHERE clause
    	    null,                                     // don't group the rows
    	    null,                                     // don't filter by row groups
    	    null                                 // The sort order
    	    );
    	long count = c.getCount();
    	c.close();
        return count;
    }
    
    public List<Tribunal> selectTribunais() {
    	// Define a projection that specifies which columns from the database
    	// you will actually use after this query.
    	String[] projection = {
    			EAdvogadoContract.TribunalTable._ID,
    			EAdvogadoContract.TribunalTable.COLUMN_NAME_TRIBUNAL_ID,
    			EAdvogadoContract.TribunalTable.COLUMN_NAME_NOME,
    			EAdvogadoContract.TribunalTable.COLUMN_NAME_SIGLA,
    			EAdvogadoContract.TribunalTable.COLUMN_NAME_ENDPOINT_1G,
    			EAdvogadoContract.TribunalTable.COLUMN_NAME_ENDPOINT_2G,
    			EAdvogadoContract.TribunalTable.COLUMN_NAME_QTD_CONSULTAS
    	};

    	// How you want the results sorted in the resulting Cursor
    	String sortOrder =
    		EAdvogadoContract.TribunalTable.COLUMN_NAME_QTD_CONSULTAS + " DESC, " +
    		EAdvogadoContract.TribunalTable.COLUMN_NAME_SIGLA + " ASC";

    	Cursor c = getReadableDatabase().query(
    			EAdvogadoContract.TribunalTable.TABLE_NAME,  // The table to query
    	    projection,                               // The columns to return
    	    null,                                // The columns for the WHERE clause
    	    null,                            // The values for the WHERE clause
    	    null,                                     // don't group the rows
    	    null,                                     // don't filter by row groups
    	    sortOrder                                 // The sort order
    	    );
    	List<Tribunal> tribunais = new ArrayList<Tribunal>();
    	if (c.moveToFirst()) {
    		do {
    			Tribunal tribunal = new Tribunal();
        		tribunal.setKey(new com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Key());
        		tribunal.getKey().setId(c.getLong(1));
    			tribunal.setNome(c.getString(2));
    			tribunal.setSigla(c.getString(3));
    			tribunal.setPje1gEndpoint(c.getString(4));
    			tribunal.setPje2gEndpoint(c.getString(5));
    			tribunal.set(EAdvogadoContract.TribunalTable.COLUMN_NAME_QTD_CONSULTAS, c.getInt(6));
    			tribunais.add(tribunal);
    		} while(c.moveToNext());
    	}
    	c.close();

        return tribunais;
    }
    
    public long selectProcessosCount() {
    	// Define a projection that specifies which columns from the database
    	// you will actually use after this query.
    	String[] projection = {
    			EAdvogadoContract.ProcessoTable._ID
    	};

    	Cursor c = getReadableDatabase().query(
    			EAdvogadoContract.ProcessoTable.TABLE_NAME,  // The table to query
    	    projection,                               // The columns to return
    	    null,                                // The columns for the WHERE clause
    	    null,                            // The values for the WHERE clause
    	    null,                                     // don't group the rows
    	    null,                                     // don't filter by row groups
    	    null                                 // The sort order
    	    );
    	long count = c.getCount();
    	c.close();
        return count;
    }
    
    
    public List<Processo> selectProcessos(boolean loadJson) {
    	// Define a projection that specifies which columns from the database
    	// you will actually use after this query.
    	String[] projection = {
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_ID_PROCESSO,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_NPU,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_ID_TRIBUNAL,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_TIPO_JUIZO,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_CONTEUDO,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_ATIVO,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_PASSIVO
    	};

    	// How you want the results sorted in the resulting Cursor
    	String sortOrder =
    		EAdvogadoContract.ProcessoTable.COLUMN_NAME_NPU + " ASC";

    	Cursor c = getReadableDatabase().query(
    			EAdvogadoContract.ProcessoTable.TABLE_NAME,  // The table to query
    	    projection,                               // The columns to return
    	    null,                                // The columns for the WHERE clause
    	    null,                            // The values for the WHERE clause
    	    null,                                     // don't group the rows
    	    null,                                     // don't filter by row groups
    	    sortOrder                                 // The sort order
    	    );
    	List<Processo> processos = new ArrayList<Processo>();
    	if (c.moveToFirst()) {
    		do {
    			Processo processo = new Processo();
        		processo.setKey(new Key()); 
        		processo.getKey().setId(c.getLong(0));
    			processo.setNpu(c.getString(1));
    			processo.setTribunal(new Key());
    			processo.getTribunal().setId(c.getLong(2));
    			processo.setTipoJuizo(c.getString(3));
    			if (loadJson) {
    				String processoJudicial = c.getString(4);
    				if (processoJudicial != null && processoJudicial.length() > 0){
    					processo.setProcessoJudicial(JsonUtils.parseQuietly(processoJudicial,
    							TipoProcessoJudicial.class));
    				}
    			}
    			processo.set(EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_ATIVO, c.getString(5));
    			processo.set(EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_PASSIVO, c.getString(6));
    			processos.add(processo);
    		} while(c.moveToNext());
    	}
    	c.close();

        return processos;
    }
    
}
