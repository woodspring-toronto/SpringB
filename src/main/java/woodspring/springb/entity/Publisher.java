package woodspring.springb.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="Publisher")
public class Publisher implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		@Column(name="id")
	    private Integer id;
		
		@Column(name="company")
		String company;
		
		@Column(name="EUI")
		String eui;	
		
		@ManyToMany(mappedBy = "publishers", fetch = FetchType.LAZY)
		@JsonBackReference
		Set<Book> books ; //= new HashSet<Book>();
		
		@Column(name="created_DT")
		Timestamp created_dt;

		@Column(name="updated_DT")
		Timestamp updated_dt;

		public Publisher() {
			super();
			this.company = "TORONTO";
			this.eui = "549-594-594";
		}
		public Publisher(String company, String eui) {
			super();
			this.company = company;
			this.eui = eui;
		}

		public Publisher(String company, String eui, Set<Book> books) {
			super();
			this.company = company;
			this.eui = eui;
			this.books = books;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
		}

		public String getEui() {
			return eui;
		}

		public void setEui(String eui) {
			this.eui = eui;
		}

		//@ManyToMany(mappedBy = "publishers", fetch = FetchType.LAZY)
		//@JsonBackReference
		public Set<Book> getBooks() {
			return books;
		}

		
		public void setBooks(Set<Book> books) {
			this.books = books;
		}

		public String getCreated_dt() {
			String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.created_dt);
			return dateString;
		}

		public void setCreated_dt(Timestamp created_dt) {
			this.created_dt = created_dt;
		}

		public String getUpdated_dt() {
			String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.updated_dt);
			return dateString;
		}

		public void setUpdated_dt(Timestamp updated_dt) {
			this.updated_dt = updated_dt;
		}

		@Override
		public String toString() {
			return "Publisher [id=" + id + ", company=" + company + ", eui=" + eui 
					+ ", created_dt=" + getCreated_dt()	+ ", updated_dt=" + getUpdated_dt() + "]";
		}

}
