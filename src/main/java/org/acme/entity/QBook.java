package org.acme.entity;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.acme.utils.DateAdapter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@XmlRootElement(name = "Employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class QBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlElement(name = "id")
    @JsonbProperty("id")
    private Long id;
    @XmlElement(name = "title")
    @JsonbProperty("title")
    private String title;
    @XmlElement(name = "numPages")
    @JsonbProperty("numPages")
    private int numPages;
    @XmlElement(name = "pubDate")
    @JsonbProperty("pubDate")
    @XmlJavaTypeAdapter(DateAdapter.class)
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate pubDate;
    @XmlElement(name = "description")
    @JsonbProperty("description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public LocalDate getPubDate() {
        return pubDate;
    }

    public void setPubDate(LocalDate pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QBook qBook = (QBook) o;
        return numPages == qBook.numPages &&
                title.equals(qBook.title) &&
                pubDate.equals(qBook.pubDate) &&
                description.equals(qBook.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, numPages, pubDate, description);
    }

    @Override
    public String toString() {
        return "QBook{" +
                "title='" + title + '\'' +
                ", numPages=" + numPages +
                ", pubDate=" + pubDate +
                ", description='" + description + '\'' +
                '}';
    }
}
