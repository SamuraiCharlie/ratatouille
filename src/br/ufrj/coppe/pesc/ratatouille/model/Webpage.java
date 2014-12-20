package br.ufrj.coppe.pesc.ratatouille.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Representa uma página da web.
 *
 */
@Entity
@Table(name="webpage")
public class Webpage {

	
	private String url;
	private String content;

	@Column(name="content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	@Id
	@Column(name="id")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
