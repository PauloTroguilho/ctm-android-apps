/**
 * 
 */
package br.com.clebertm.procurados;

import java.io.File;
import java.io.InputStream;

import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import org.xml.sax.InputSource;

/**
 * @author Cleber Moura <cleber.moura@tjpe.jus.br>
 *
 */
public class SDSCrawler {
	
	private ScraperConfiguration config;
	private Scraper scraper;

	/**
	 * 
	 */
	public SDSCrawler() {
		InputStream fileConfig = getClass().getResourceAsStream("procurados_config.xml");
		if (fileConfig == null) {
			fileConfig = getClass().getResourceAsStream("/procurados_config.xml");
		}
		config = new ScraperConfiguration(new InputSource(fileConfig));
		String workdir = System.getenv("PROCURADOS_WORKDIR");
		workdir = "D:\\sds_workdir";
		File f = new File(workdir);
		if (!f.exists()) {
			f.mkdirs();
		}
		scraper = new Scraper(config, workdir);
		scraper.setDebug(true); 
		scraper.execute();
	}
	
	public static void main(String[] args) {
		SDSCrawler crawler = new SDSCrawler();
	}

}
