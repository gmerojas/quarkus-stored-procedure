package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.entity.QBook;
import org.acme.repository.QBookRepository;
import org.acme.service.QBookService;

import java.util.List;

@Path("/books")
@Transactional
public class QBookController {
    @Inject
    private QBookRepository repo;

    @Inject
    private QBookService service;

    @GET
    public List<QBook> index(){
        return repo.listAll();
    }

    @GET
    @Path("/all")
    public List<QBook> index2(){
        return service.getQBooks();
    }

    @GET
    @Path("/allpa")
    public List<QBook> index3(){
        List<QBook> listBook = service.getBooksPA();
        for (QBook qBook : listBook) {
            System.out.println("Element " + qBook.getId());
        }
        return listBook;
    }

    @GET
    @Path("/allpa/{id}")
    public QBook index3(@PathParam("id") Long id){
        QBook qBook = service.getBookByIdPA(id);
        System.out.println("Element " + qBook.getId());
        return qBook;
    }

    @GET
    @Path("/allpa2")
    public List<QBook> index4(){
        List<QBook> listBook = service.getLisBooksPA2();
        for (QBook qBook : listBook) {
            System.out.println("Element " + qBook.getId());
        }
        return listBook;
    }

    /*@GET
    @Path("/allpa3")
    public List<QBook> index5(){
        List<QBook> listBook = service.getLisBooksPA3();
        for (QBook qBook : listBook) {
            System.out.println("Element " + qBook.getId());
        }
        return listBook;
    }*/

    @GET
    @Path("/{id}")
    public QBook index4(@PathParam("id") Long id){
        return service.getQBookById(id);
    }

    @GET
    @Path("/max")
    public Long maximo(){
        return service.getMaxQBookId();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public QBook insert(QBook insertedBook){
        repo.persist(insertedBook);
        return insertedBook;
    }
}
