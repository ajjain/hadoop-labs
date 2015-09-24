package in.ajjain.ci.dao;

import in.ajjain.ci.common.hbase.HBaseTemplate;

/**
 * The Class AbstractDAO.
 */
public class AbstractDAO {
	
	/** The template. */
	private HBaseTemplate template;
	
	/** The tablename. */
	private String tablename;
	
	/**
	 * @return the tablename
	 */
	public String getTablename() {
		return tablename;
	}

	/**
	 * @param tablename the tablename to set
	 */
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	/**
	 * Instantiates a new abstract dao.
	 *
	 * @param template the template
	 */
	public AbstractDAO(HBaseTemplate template) {
		this.template = template;
	}
	
	/**
	 * Gets the template.
	 *
	 * @return the template
	 */
	public HBaseTemplate getTemplate() {
		return template;
	}
	
	/**
	 * Sets the template.
	 *
	 * @param template the template to set
	 */
	public void setTemplate(HBaseTemplate template) {
		this.template = template;
	}

}
