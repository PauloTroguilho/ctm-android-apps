/**
 * 
 */
package br.com.clebertm.procurados;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.io.FileUtils;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import org.xml.sax.InputSource;

import br.com.clebertm.procurados.types.Procurado;
import br.com.clebertm.procurados.types.Procurados;
import br.com.clebertm.procurados.util.MarshallerUtil;

/**
 * @author Cleber Moura <cleber.moura@tjpe.jus.br>
 * 
 */
public class SDSCrawler {

	private static final String FOTOS_DIR = "fotos";

	private static final String PROCURADOS_XML = "procurados.xml";

	public static final String PROCURADOS_CONFIG_XML = "D:\\Develop\\DesenvolvimentoAndroid\\workspace\\procurados-web\\src\\main\\resources\\procurados_config.xml";
	
	private ScraperConfiguration config;
	private Scraper scraper;

	/**
	 * @throws FileNotFoundException 
	 * 
	 */
	public SDSCrawler(String workDir) throws FileNotFoundException {
		InputStream fileConfig = new FileInputStream(PROCURADOS_CONFIG_XML);
		if (fileConfig == null) {
			fileConfig = getClass().getResourceAsStream(File.separator 
					+ PROCURADOS_CONFIG_XML);
		}
		config = new ScraperConfiguration(new InputSource(fileConfig));
		File f = new File(workDir);
		if (!f.exists()) {
			f.mkdirs();
		}
		scraper = new Scraper(config, workDir);
		scraper.setDebug(true);
	}
	
	/**
	 * 
	 */
	public void execute() {
		scraper.execute();
	}

	public static void main(String[] args) throws IOException, JAXBException, XMLStreamException {
		
		args = new String[]{"D:\\sds_workdir", "D:\\sds_workdir\\destDir"};
		
		if (args != null && args.length == 2) {
			String workDirPath = args[0];
			String destDirPath = args[1];
			
			File destDir = new File(destDirPath);
			
			if (destDir.exists()) {
				
				SDSCrawler crawler = new SDSCrawler(workDirPath);
				crawler.execute();
				
				File workDir = new File(workDirPath);
				File workXmlFile = new File(workDir, PROCURADOS_XML);
				
				if (workXmlFile.exists()) {
					
					String strContent = FileUtils.readFileToString(workXmlFile);
					Procurados procurados = MarshallerUtil.unmarshal(strContent, Procurados.class);
					
					if (procurados != null && !procurados.getProcurado().isEmpty()) {
						
						File destXmlFile = new File(destDir, PROCURADOS_XML);
						FileUtils.copyFile(workXmlFile, destXmlFile);
						
						File workFotosDir = new File(workDir, FOTOS_DIR);
						
						if (workFotosDir.exists()) {
							File[] fotoFiles = workFotosDir.listFiles();
							List<File> filesToDelete = new ArrayList<File>();
							
							for (File file : fotoFiles) {
								String fotoId = file.getName().split("_")[1];
								if (fotoId.endsWith("jpeg")) {
									fotoId = fotoId.substring(0, fotoId.indexOf("."));
								}
								boolean found = false;
								for (Procurado p : procurados.getProcurado()) {
									if (p.getFotoId().equalsIgnoreCase(fotoId)) {
										found = true;
										break;
									}
								}
								if (!found) {
									filesToDelete.add(file);
								}
							}
							
							for (File file : filesToDelete) {
								FileUtils.deleteQuietly(file);
								System.out.println("Foto deletada: " + file.getAbsolutePath());
							}
							
							File destFotosDir = new File(destDir, FOTOS_DIR);
							if (destFotosDir.exists()) {
								FileUtils.deleteDirectory(destFotosDir);
								System.out.println("Diretorio '"+destFotosDir.getAbsolutePath()+"' deletado.");
							}
							
							FileUtils.copyDirectory(workFotosDir, destFotosDir);
							System.out.println("Diretorio '"+workFotosDir.getAbsolutePath()+"' copiado para '"+destFotosDir.getAbsolutePath()+"'");
						}
					}
				}
				
			} else {
				System.out.println("Diretorio '"+destDirPath+"' não encontrado.");
			}
		}
	}
}
