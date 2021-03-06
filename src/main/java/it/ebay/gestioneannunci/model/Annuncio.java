package it.ebay.gestioneannunci.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "annuncio")
public class Annuncio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(columnDefinition="tinyInt(1) default 1", name = "stato_annuncio")
	private Boolean aperto;
	
	@NotBlank(message = "{testoAnnuncio.notblank}", groups = (EditAnnuncioParam.class))
	@Column(name = "testo_annuncio")
	private String testoAnnuncio;

	@NotNull(message = "{prezzo.notnull}", groups = (EditAnnuncioParam.class))
	@Column(name = "prezzo")
	private Double prezzo;
	
	@NotNull(message = "{dataPubblicazione.notnull}")
	@Column(name = "data_pubblicazione")
	private Date dataPubblicazione;
	
	@NotNull(message = "{utente.notnull}")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utente_id")
	private Utente utente;
	
	@ManyToMany(mappedBy = "annunci")
	private Set<Categoria> categorie = new HashSet<>();

	public Annuncio() {}

	public Annuncio(Boolean aperto, @NotBlank(message = "{testoAnnuncio.notnull}") String testoAnnuncio,
			@NotNull(message = "{prezzo.notnull}") Double prezzo,
			@NotNull(message = "{dataPubblicazione.notnull}") Date dataPubblicazione) {
		this.aperto = aperto;
		this.testoAnnuncio = testoAnnuncio;
		this.prezzo = prezzo;
		this.dataPubblicazione = dataPubblicazione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean isAperto() {
		return aperto;
	}

	public void setAperto(Boolean aperto) {
		this.aperto = aperto;
	}

	public String getTestoAnnuncio() {
		return testoAnnuncio;
	}

	public void setTestoAnnuncio(String testoAnnuncio) {
		this.testoAnnuncio = testoAnnuncio;
	}

	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}

	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	public Set<Categoria> getCategorie() {
		return categorie;
	}

	public void setCategorie(Set<Categoria> categorie) {
		this.categorie = categorie;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	@Override
	public String toString() {
		return "Annuncio{" +
				"id=" + id +
				", aperto=" + aperto +
				", testoAnnuncio='" + testoAnnuncio + '\'' +
				", prezzo=" + prezzo +
				", dataPubblicazione=" + dataPubblicazione +
				", categorie" + categorie +
				'}';
	}

}
