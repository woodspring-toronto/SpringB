package woodspring.springb.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.JoinColumn;

@Entity
@Table(name="Book")
//@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Integer id;
	
	@Column(name="title")
	String title;
	
	@Column(name="author")
	String author;

	@Column(name="created_DT")
	Timestamp created_dt;

	@Column(name="updated_DT")
	Timestamp updated_dt;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "Book_Publisher", 
			joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"), 
			inverseJoinColumns = @JoinColumn(name = "publisher_id", referencedColumnName = "id"))
	@JsonManagedReference
	Set<Publisher> publishers;// = new HashSet<Publisher>();
	
	public Book() {
		super();
		this.title = "My DUMMY Book";
		this.author = "Alfred Huang";
	}
	public Book(String title, String author) {
		super();
		this.title = title;
		this.author = author;
	}

	public Book(String title, String author, Set<Publisher> publishers) {
		super();
		this.title = title;
		this.author = author;
		this.publishers = publishers;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	/*@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Book_Publisher", 
			joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"), 
			inverseJoinColumns = @JoinColumn(name = "publisher_id", referencedColumnName = "id"))
			*/
	public Set<Publisher> getPublishers() {
		return publishers;
	}
	
	private String getPublishersString() {
		StringBuilder strBuilder = new StringBuilder();
		if (publishers!=null) {
			publishers.stream().forEach(item -> strBuilder.append("{"+ item.toString()+"}"));
		}
		return strBuilder.toString();
	}

	public void setPublishers(Set<Publisher> publishers) {
		this.publishers = publishers;
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
		return "Book [id=" + id + ", title=" + title + ", author=" + author 
				+ ", publishers=" + getPublishersString()
				+ ", created_dt=" + getCreated_dt()	+ ", updated_dt=" + getUpdated_dt() + "]";
	}



}
