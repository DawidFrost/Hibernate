package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(name = "product.all", query = "SELECT p FROM Product p"),
        @NamedQuery(name = "product.byPrice", query = "SELECT p FROM Product p where p.price>=:priceFrom and p.price<=:priceTo"),
        @NamedQuery(name = "product.byName", query = "SELECT p FROM Product p where p.name=:productName"),
        @NamedQuery(name = "product.byCategory", query = "SELECT p FROM Product p where p.productCategory=:productCategory"),
        @NamedQuery(name = "product.id", query = "FROM Product p WHERE p.id=:productId")
})

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Collection<ProductComment> comments=new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Collection<ProductComment> getComments() {
        return comments;
    }

    public void setComments(Collection<ProductComment> comments) {
        this.comments = comments;
    }
}
