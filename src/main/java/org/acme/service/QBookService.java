package org.acme.service;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.acme.entity.QBook;

import java.io.StringReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class QBookService {

    @Inject
    private EntityManager entityManager;

    public List<QBook> getQBooks(){
        return entityManager.createQuery("SELECT c FROM QBook c").getResultList();
    }

    public QBook getQBookById(Long bookId){
        return (QBook) entityManager.createQuery("SELECT c FROM QBook c where id = " + bookId).getSingleResult();
    }

    public Long getMaxQBookId(){
        return (Long) entityManager.createQuery("SELECT max(c.id) FROM QBook c").getSingleResult();
    }

    public List<QBook> getBooksPA() {

        StoredProcedureQuery storedProcedureQuery=entityManager
                .createStoredProcedureQuery("GET_BOOKS");

        //storedProcedureQuery.registerStoredProcedureParameter("vr_opcion", Integer.class, ParameterMode.IN)
        //storedProcedureQuery.registerStoredProcedureParameter("vs_numcob", Integer.class, ParameterMode.OUT)
        storedProcedureQuery.registerStoredProcedureParameter("c3", String.class, ParameterMode.REF_CURSOR);

        storedProcedureQuery.execute();

        //String msgRespuesta = (String) storedProcedureQuery.getOutputParameterValue("vs_msgRpta");

        return ListObjectsToListQBook(storedProcedureQuery.getResultList());
    }

    public List<QBook> getLisBooksPA2() {

        List<QBook> listado = new ArrayList<>();

        StoredProcedureQuery storedProcedureQuery=entityManager
                .createStoredProcedureQuery("GET_LIST_BOOKS");

        //storedProcedureQuery.registerStoredProcedureParameter("vr_opcion", Integer.class, ParameterMode.IN)
        //storedProcedureQuery.registerStoredProcedureParameter("vs_numcob", Integer.class, ParameterMode.OUT)
        //storedProcedureQuery.registerStoredProcedureParameter("c3", String.class, ParameterMode.REF_CURSOR);
        storedProcedureQuery.registerStoredProcedureParameter("p_json_out", String.class, ParameterMode.OUT);
        storedProcedureQuery.registerStoredProcedureParameter("p_xml_out", String.class, ParameterMode.OUT);

        storedProcedureQuery.execute();

        String jsonResult = (String) storedProcedureQuery.getOutputParameterValue("p_json_out");
        //String jsonResult = (String) storedProcedureQuery.getOutputParameterValue("p_xml_out");

        jsonResult = jsonResult.replace("&quot;","\"");

        System.out.println(jsonResult);

        JsonbConfig config = new JsonbConfig().withNullValues(true);
        Jsonb jsonb = JsonbBuilder.create(config);

        listado = jsonb.fromJson(jsonResult, new ArrayList<QBook>(){}.getClass().getGenericSuperclass());

        return listado;
    }

    /*public List<QBook> getLisBooksPA3() {

        List<QBook> listado = new ArrayList<>();

        StoredProcedureQuery storedProcedureQuery=entityManager
                .createStoredProcedureQuery("GET_LIST_BOOKS");

        //storedProcedureQuery.registerStoredProcedureParameter("vr_opcion", Integer.class, ParameterMode.IN)
        //storedProcedureQuery.registerStoredProcedureParameter("vs_numcob", Integer.class, ParameterMode.OUT)
        //storedProcedureQuery.registerStoredProcedureParameter("c3", String.class, ParameterMode.REF_CURSOR);
        storedProcedureQuery.registerStoredProcedureParameter("p_json_out", String.class, ParameterMode.OUT);
        storedProcedureQuery.registerStoredProcedureParameter("p_xml_out", String.class, ParameterMode.OUT);

        storedProcedureQuery.execute();

        String xmlResult = (String) storedProcedureQuery.getOutputParameterValue("p_json_out");
        //String jsonResult = (String) storedProcedureQuery.getOutputParameterValue("p_xml_out");

        // Usar JAXB para convertir el XML a objetos Java
        JAXBContext jaxbContext = JAXBContext.newInstance(QBook.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(xmlResult);
        QBook employeesWrapper = (QBook) unmarshaller.unmarshal(reader);
        employees = employeesWrapper.getEmployees();

        return listado;
    }*/

    public QBook getBookByIdPA(Long id) {

        StoredProcedureQuery storedProcedureQuery=entityManager
                .createStoredProcedureQuery("GET_BOOKS_BY_ID");


        storedProcedureQuery.registerStoredProcedureParameter("p_id", Long.class, ParameterMode.IN);
        //storedProcedureQuery.registerStoredProcedureParameter("vs_numcob", Integer.class, ParameterMode.OUT)
        storedProcedureQuery.registerStoredProcedureParameter("c3", String.class, ParameterMode.REF_CURSOR);

        storedProcedureQuery.setParameter("p_id", id);

        storedProcedureQuery.execute();

        return objectToQBook((Object[]) storedProcedureQuery.getSingleResult());
    }

    private List<QBook> ListObjectsToListQBook(List<Object[]> listObjects){

        List<QBook> result = new ArrayList<>(listObjects.size());

        for (Object[] row : listObjects) {
            QBook o = objectToQBook(row);

            result.add(o);
        }
        return result;
    }

    private QBook objectToQBook(Object[] row){

        QBook o = new QBook();
        o.setId((Long) row[0]);
        o.setTitle((String) row[1]);
        o.setNumPages((Integer) row[2]);
        o.setDescription((String) row[3]);
        o.setPubDate(row[4] != null ? ((Timestamp)row[4]).toLocalDateTime().toLocalDate() : null);
        return o;
    }
}
