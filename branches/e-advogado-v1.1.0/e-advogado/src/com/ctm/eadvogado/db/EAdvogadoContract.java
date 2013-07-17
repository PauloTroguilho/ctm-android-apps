package com.ctm.eadvogado.db;

import android.provider.BaseColumns;

public class EAdvogadoContract {

	private EAdvogadoContract() {}
	
	public static abstract class ProcessoTable implements BaseColumns {
	    public static final String TABLE_NAME = "processos";
	    public static final String COLUMN_NAME_NPU = "npu";
	    public static final String COLUMN_NAME_ID_TRIBUNAL = "tribunal_id";
	    public static final String COLUMN_NAME_TIPO_JUIZO = "tipo_juizo";
	}
	public static abstract class TribunalTable implements BaseColumns {
	    public static final String TABLE_NAME = "tribunais";
	    public static final String COLUMN_NAME_TRIBUNAL_ID = "tribunal_id";
	    public static final String COLUMN_NAME_NOME = "nome";
	    public static final String COLUMN_NAME_SIGLA = "sigla";
	}
	public static abstract class LancamentosTable implements BaseColumns {
	    public static final String TABLE_NAME = "lancamentos";
	    public static final String COLUMN_NAME_SKU = "sku";
	    public static final String COLUMN_NAME_ORDER_ID = "order_id";
	    
	    public static final String TIPO_LANC_CREDITO = "C";
	    public static final String TIPO_LANC_DEBITO = "D";
	    public static final String TIPO_LANC_CONTA_PREMIUM = "P";
	}
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String COMMA_SEP = ",";
	
	public static final String SQL_CREATE_PROCESSOS =
	    "CREATE TABLE " + EAdvogadoContract.ProcessoTable.TABLE_NAME + " (" +
	    EAdvogadoContract.ProcessoTable._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
	    EAdvogadoContract.ProcessoTable.COLUMN_NAME_NPU + TEXT_TYPE + COMMA_SEP +
	    EAdvogadoContract.ProcessoTable.COLUMN_NAME_ID_TRIBUNAL + INTEGER_TYPE + COMMA_SEP +
	    EAdvogadoContract.ProcessoTable.COLUMN_NAME_TIPO_JUIZO + TEXT_TYPE +
	    " )";

	public static final String SQL_DELETE_PROCESSOS =
	    "DROP TABLE IF EXISTS " + EAdvogadoContract.ProcessoTable.TABLE_NAME;
	
	public static final String SQL_DELETE_TRIBUNAIS =
	    "DROP TABLE IF EXISTS " + EAdvogadoContract.TribunalTable.TABLE_NAME;
	
	public static final String SQL_DELETE_LANCAMENTOS =
		"DROP TABLE IF EXISTS " + EAdvogadoContract.LancamentosTable.TABLE_NAME;
	
	public static final String SQL_CREATE_TRIBUNAIS =
	    "CREATE TABLE " + EAdvogadoContract.TribunalTable.TABLE_NAME + " (" +
	    EAdvogadoContract.TribunalTable._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
	    EAdvogadoContract.TribunalTable.COLUMN_NAME_TRIBUNAL_ID + INTEGER_TYPE + COMMA_SEP +
	    EAdvogadoContract.TribunalTable.COLUMN_NAME_NOME + TEXT_TYPE + COMMA_SEP +
	    EAdvogadoContract.TribunalTable.COLUMN_NAME_SIGLA + TEXT_TYPE +
	    " )";
	
	public static final String SQL_CREATE_LANCAMENTOS =
	    "CREATE TABLE " + EAdvogadoContract.LancamentosTable.TABLE_NAME + " (" +
	    EAdvogadoContract.LancamentosTable._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
	    EAdvogadoContract.LancamentosTable.COLUMN_NAME_SKU + TEXT_TYPE + COMMA_SEP +
	    EAdvogadoContract.LancamentosTable.COLUMN_NAME_ORDER_ID + TEXT_TYPE +
	    " )";


}
