package org.agoncal.quarkus.panache.repository;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.agoncal.quarkus.jdbc.Artist;
import org.agoncal.quarkus.jpa.Customer;
import org.agoncal.quarkus.panache.model.Book;
import org.agoncal.quarkus.panache.model.Language;
import org.agoncal.quarkus.panache.model.OrderLine;
import org.agoncal.quarkus.panache.model.Publisher;
import org.agoncal.quarkus.panache.model.PurchaseOrder;
import org.junit.jupiter.api.Test;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;

//import org.agoncal.quarkus.panache.model.Publisher;

@QuarkusTest
public class PurchaseOrderRepositoryTest {

  @Inject 
  CustomerRepository customerRepository;
  
  @Test
  @TestTransaction
  public void shouldCreateAndFindPurchaseOrder(){

    //create an artist
    Artist artist = new Artist("artist name", "artist bio");

    //create a Publisher
    Publisher publisher = new Publisher("publisher name");

    //create a book
    Book book=new Book();
    book.title="title of book";
    book.nbOfPages=500;
    book.language=Language.ENGLISH;
    book.price= new BigDecimal(10);
    book.isbn="isbn";
    
    //set relationships
    book.publisher=publisher;
    book.artist=artist;

    //persist book
    Book.persist(book);

    //create a customer
    Customer customer = new Customer("customer first name","customer last name","customer email");
    customerRepository.persist(customer);

    //create an orderline
    OrderLine orderLine = new OrderLine();
    orderLine.item=book;
    orderLine.quantity=2;

    //create a purchase order
    PurchaseOrder purchaseOrder  = new PurchaseOrder();
    purchaseOrder.customer=customer;
    purchaseOrder.addOrderLine(orderLine);
    //persist purchase order
    PurchaseOrder.persist(purchaseOrder);
  }

}