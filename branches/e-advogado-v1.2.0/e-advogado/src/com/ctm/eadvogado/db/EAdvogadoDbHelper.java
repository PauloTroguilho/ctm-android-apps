package com.ctm.eadvogado.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ctm.eadvogado.endpoints.processoEndpoint.model.Key;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.Processo;
import com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal;

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
    public void insertProcessoSeNaoExiste(Processo processo) {
    	Processo eProcesso = selectProcesso(processo.getNpu(),
				processo.getTribunal().getId(),
				processo.getTipoJuizo());
		if (eProcesso == null) {
			inserirProcesso(processo);
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
    
    
    public List<Processo> selectProcessos() {
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
    	List<Processo> processos = new ArrayList<Processo>();
    	if (c.moveToFirst()) {
    		do {
    			Processo processo = new Processo();
    			processo.setKey(new Key());
    			processo.getKey().setId(c.getLong(0));
    			processo.setNpu(c.getString(1));
        		processo.setTipoJuizo(c.getString(2));
        		Tribunal tribunal = this.selectTribunalPorId(c.getLong(3));
        		if (tribunal != null) {
        			processo.put("tribunal.sigla", tribunal.getSigla());
            		processo.put("tribunal.nome", tribunal.getNome());
        		}
    			processos.add(processo);
    		} while(c.moveToNext());
    	}
    	c.close();

        return processos;
    }
}