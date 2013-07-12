package com.ctm.eadvogado.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ctm.eadvogado.dto.ProcessoDTO;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.Key;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.Processo;
import com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal;

public class EAdvogadoDbHelper extends SQLiteOpenHelper {
	// If you change the database schema, you must increment the database
	// version.
	public static final int DATABASE_VERSION = 4;
	public static final String DATABASE_NAME = "EAdvogado.db";
	
	protected final Context mHelperContext;

	public EAdvogadoDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mHelperContext = context;
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(EAdvogadoContract.SQL_CREATE_TRIBUNAIS);
		db.execSQL(EAdvogadoContract.SQL_CREATE_PROCESSOS);
		db.execSQL(EAdvogadoContract.SQL_CREATE_LANCAMENTOS);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy
		// is to simply to discard the data and start over
		
		//db.execSQL(EAdvogadoContract.SQL_DELETE_PROCESSOS);
		//db.execSQL(EAdvogadoContract.SQL_DELETE_TRIBUNAIS);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//onUpgrade(db, oldVersion, newVersion);
		db.execSQL(EAdvogadoContract.SQL_DELETE_LANCAMENTOS);
	}
	
	/**
	 * Insere um lançamento
     * @param sku
     * @param orderId
     * @return
     */
    public long inserirLancamento(String sku, String orderId) {
        ContentValues initialValues = new ContentValues();
		initialValues.put(EAdvogadoContract.LancamentosTable.COLUMN_NAME_SKU,
				sku);
		initialValues.put(EAdvogadoContract.LancamentosTable.COLUMN_NAME_ORDER_ID,
				orderId);
        return getWritableDatabase().insert(EAdvogadoContract.LancamentosTable.TABLE_NAME, null, initialValues);
    }
    
    /**
     * @param id
     * @return
     */
    public long removerLancamento(Long id) {
		String whereClause = EAdvogadoContract.LancamentosTable._ID + " = ?";
		String[] whereArgs = new String[] {id.toString()};

        return getWritableDatabase().delete(EAdvogadoContract.LancamentosTable.TABLE_NAME, whereClause, 
        		whereArgs);
    }
    
    
    /**
     * @return
     */
    public long selectQtdeProcessosDisponiveis() {
    	Cursor c = getReadableDatabase().rawQuery(
    			"SELECT SUM("+EAdvogadoContract.LancamentosTable._ID+") FROM " 
    			+ EAdvogadoContract.LancamentosTable.TABLE_NAME + " WHERE " 
    			+ EAdvogadoContract.LancamentosTable.COLUMN_NAME_SKU + " = ?", 
    			new String[] { EAdvogadoContract.LancamentosTable.TIPO_LANC_CREDITO });
    	long qtdeCredito = 0;
    	if(c.moveToFirst()) {
    	    qtdeCredito = c.getInt(0);
    	}
    	c = getReadableDatabase().rawQuery(
    			"SELECT SUM("+EAdvogadoContract.LancamentosTable._ID+") FROM " 
    			+ EAdvogadoContract.LancamentosTable.TABLE_NAME + " WHERE " 
    			+ EAdvogadoContract.LancamentosTable.COLUMN_NAME_SKU + " = ?", 
    			new String[] { EAdvogadoContract.LancamentosTable.TIPO_LANC_DEBITO });
    	long qtdeDebito = 0;
    	if(c.moveToFirst()) {
    	    qtdeCredito = c.getInt(0);
    	}
    	return qtdeCredito - qtdeDebito;
    }
    
    /**
     * @return
     */
    public boolean possuiContaPremium() {
    	// Define a projection that specifies which columns from the database
    	// you will actually use after this query.
    	String[] projection = {
    			EAdvogadoContract.LancamentosTable._ID
    	};
    	String selection = EAdvogadoContract.LancamentosTable.COLUMN_NAME_SKU + " = ?";
	   	String[] columnValues = {
	   			EAdvogadoContract.LancamentosTable.TIPO_LANC_CONTA_PREMIUM
	   	};

    	Cursor c = getReadableDatabase().query(
    			EAdvogadoContract.ProcessoTable.TABLE_NAME,	// The table to query
    	    projection,										// The columns to return
    	    selection,										// The columns for the WHERE clause
    	    columnValues,									// The values for the WHERE clause
    	    null,											// don't group the rows
    	    null,											// don't filter by row groups
    	    null											// The sort order
    	    );
    	long count = c.getCount();
    	c.close();
        return count > 0;
    }
	
	
    /**
     * Insere um {@link Processo} no banco de dados
     * @param processo
     * @param xmlData
     * @return
     */
    public long inserirProcesso(Processo processo) {
        ContentValues initialValues = new ContentValues();
		initialValues.put(EAdvogadoContract.ProcessoTable.COLUMN_NAME_NPU,
				processo.getNpu());
		initialValues.put(EAdvogadoContract.ProcessoTable.COLUMN_NAME_ID_TRIBUNAL,
				processo.getTribunal().getId());
		initialValues.put(EAdvogadoContract.ProcessoTable.COLUMN_NAME_TIPO_JUIZO,
				processo.getTipoJuizo());

        return getWritableDatabase().insert(EAdvogadoContract.ProcessoTable.TABLE_NAME, null, initialValues);
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
        			
        		}
    		}
    	}
    }
    
    /**
     * @param npu
     * @param idTribunal
     * @param tipoJuizo
     * @return
     */
    public Processo selectProcesso(String npu, Long idTribunal, String tipoJuizo) {
    	// Define a projection that specifies which columns from the database
    	// you will actually use after this query.
    	String[] projection = {
    			EAdvogadoContract.ProcessoTable._ID,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_NPU,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_ID_TRIBUNAL,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_TIPO_JUIZO
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
    			EAdvogadoContract.TribunalTable.COLUMN_NAME_SIGLA
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
    	}
    	c.close();

        return tribunal;
    }
    
    public List<Tribunal> selectTribunais() {
    	// Define a projection that specifies which columns from the database
    	// you will actually use after this query.
    	String[] projection = {
    			EAdvogadoContract.TribunalTable._ID,
    			EAdvogadoContract.TribunalTable.COLUMN_NAME_TRIBUNAL_ID,
    			EAdvogadoContract.TribunalTable.COLUMN_NAME_NOME,
    			EAdvogadoContract.TribunalTable.COLUMN_NAME_SIGLA
    	};

    	// How you want the results sorted in the resulting Cursor
    	String sortOrder =
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
    			Tribunal t = new Tribunal();
        		t.setKey(new com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Key());
        		t.getKey().setId(c.getLong(1));
    			t.setNome(c.getString(2));
    			t.setSigla(c.getString(3));
    			tribunais.add(t);
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
    
    
    public List<ProcessoDTO> selectProcessos() {
    	// Define a projection that specifies which columns from the database
    	// you will actually use after this query.
    	String[] projection = {
    			EAdvogadoContract.ProcessoTable._ID,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_NPU,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_TIPO_JUIZO,
    			EAdvogadoContract.ProcessoTable.COLUMN_NAME_ID_TRIBUNAL
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
    	List<ProcessoDTO> processos = new ArrayList<ProcessoDTO>();
    	if (c.moveToFirst()) {
    		do {
    			ProcessoDTO p = new ProcessoDTO();
    			Processo processo = new Processo();
    			processo.setKey(new Key());
    			processo.getKey().setId(c.getLong(0));
    			processo.setNpu(c.getString(1));
        		processo.setTipoJuizo(c.getString(2));
        		p.setProcesso(processo);
    			p.setTribunal(this.selectTribunalPorId(c.getLong(3)));
    			processos.add(p);
    		} while(c.moveToNext());
    	}
    	c.close();

        return processos;
    }
}
