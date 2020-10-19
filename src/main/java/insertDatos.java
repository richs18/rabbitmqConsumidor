import java.sql.Connection;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class insertDatos {
    int id;
    String   nombreProducto;
    String  precioProducto;
    String  idCategoria;
    String categoriaProducto;
    String storeId;
    String storeName;

    Connection cc=null;

    Statement stmnt=null;
    Statement st=null;
    ResultSet rs=null;
    int cateId;
    int storID;
    int idProd;
    ArrayList<String> datos;
    //en esta clase implemento runaable o el  extend thread o hilo  y en  el metodo
// void run que se crea hay ejecuto el metodo insertarDdatos
    public insertDatos(Connection cc){
    this.cc=cc;
    }

    public void insertarDdatos(String datos )  {

        String palabra = datos;
        String[] partes= null;
        for (int i = 0; i < datos.length(); i++) {
            partes = palabra.split(",");
        }


            idProd=0;
            cateId=0;
            storID=0;



            System.out.println("esta es la palabra completa  "+palabra);


            id = Integer.parseInt(partes[0].substring(1,partes[0].length()));
            nombreProducto = partes[1];
            precioProducto = partes[2];
            idCategoria = partes[3];
            categoriaProducto = partes[4];
            storeId = partes[5];
            storeName = partes[6].substring(0,partes[6].length()-1);

            System.out.println("este el id "+id);
            System.out.println("este el nombre producto "+nombreProducto);
            System.out.println("este el precio producto "+precioProducto);
            System.out.println("este el id categoria  "+idCategoria);
            System.out.println("este el categoria del producto "+categoriaProducto);
            System.out.println("este el storeId "+storeId);
            System.out.println("este el storeName "+storeName);



            consultarId(id);
            consultarIdcategoria(idCategoria);
            consultarStoreId(storeId);





            try {

                Statement stmnt = null;
                stmnt =cc.createStatement();


                System.out.println("estees el dato idProd   ===="+idProd);
                if(idProd==0){

                    if(cateId==0){
                        System.out.println("entro alprimer if cateId..................");
                        String sql = "INSERT INTO category(category_id,category_name) "
                                +  "values" + "(" + idCategoria + ", " +"'"+ categoriaProducto+"'" +")";

                        String sql2 = "INSERT INTO products (products_id, products_name, category_id) "
                                +  "values" + "(" + id + "," +"'"+ nombreProducto +"'"+","+ idCategoria +")";
                        stmnt.executeUpdate(sql);
                        stmnt.executeUpdate(sql2);
                    }else{
                        String sql3 = "INSERT INTO products (products_id, products_name, category_id) "
                                +  "values" + "(" + id + "," +"'"+ nombreProducto +"'"+","+ cateId +")";
                        stmnt.executeUpdate(sql3);
                    }


                    System.out.println("este es el dato antes de entrar al if sortID"+storID);
                    if(storID==0){
                        System.out.println("entro al segundo if storID................");
                        String sql4 = "INSERT INTO store(store_id, store_name) "
                                +  "values" + "(" + storeId + "," +"'"+ storeName+"'" +")";

                        String sql5 = "INSERT INTO produc_store (product_id, store_id, product_price) "
                                +  "values" + "("+ id +","+ storeId +","+ "'"+ precioProducto +"'" +")";
                        stmnt.executeUpdate(sql4);
                        stmnt.executeUpdate(sql5);
                    }else{

                        String sql6 = "INSERT INTO produc_store (product_id, store_id, product_price) "
                                +  "values" + "("+ id +","+ storID +","+ "'"+ precioProducto +"'" +")";
                        stmnt.executeUpdate(sql6);

                    }

                }else{
                    System.out.println("aqui va el update sql");
                    if(cateId==0){
                        String sql7 = "INSERT INTO category(category_id,category_name) "
                                +  "values" + "(" + idCategoria + ", " +"'"+ categoriaProducto+"'" +")";
                        stmnt.executeUpdate(sql7);
                    }
                    if(storID==0){
                        System.out.println("entro al segundo if storID................");
                        String sql8 = "INSERT INTO store(store_id, store_name) "
                                +  "values" + "(" + storeId + "," +"'"+ storeName+"'" +")";
                        stmnt.executeUpdate(sql8);
                    }
                    String sqlUpdate = "UPDATE products " +
                            "SET  products_name =" +"'"+ nombreProducto +"'"+ ","
                            + " category_id= "+ idCategoria +
                            " WHERE products_id = "+ id +";";

                    String sqlUpdate2 = "UPDATE produc_store " +
                            "SET  store_id =" + storeId + ","
                            + " product_price= "+ precioProducto +
                            " WHERE product_id = "+ id +";";

                    stmnt.executeUpdate(sqlUpdate);
                    stmnt.executeUpdate(sqlUpdate2);

                }

                stmnt.close();
            }  catch (SQLException e) {
                System.out.println(e.getMessage());
            }

    }


    public void consultarIdcategoria(String nomCategoria){

        System.out.println("este es el nombre de la categoria "+nomCategoria);

        try{


            st=cc.createStatement();
            ResultSet rs = st.executeQuery( "SELECT category_id "+
                    "FROM category where category_id ="+"'"+ nomCategoria +"'"+";" );

//ResultSet rs = st.executeQuery( "SELECT store_id "+
//        "FROM store where store_name ="+"'"+ nomStore +"'"+";" );

//ResultSet rs = st.executeQuery( "SELECT products_id "+
//        "FROM products where products_id ="+productId+";" );

            while(rs.next()){
                String idPro=rs.getString(1);
                cateId =  Integer.parseInt(idPro);
                System.out.println("este es el dato de rs.getString "+rs.getString(1) );
                System.out.println("este es el id consultado "+ cateId);
            }
//st.close();
//cc.close();

        }
        catch(SQLException ex)
        {
            System.out.println(ex);
        }
    }


    public void consultarStoreId(String Store_name){
        try {
            st=cc.createStatement();
            ResultSet rs = st.executeQuery( "SELECT store_id "+
                    "FROM store where store_id ="+"'"+ Store_name +"'"+";" );

            while(rs.next()){
                String idPro=rs.getString(1);
                storID =  Integer.parseInt(idPro);
                System.out.println("este es el dato de rs.getString................. "+rs.getString(1) );
                System.out.println("este es el id consultado................... "+ storID);
            }
//        st.close();
//        cc.close();

        } catch (SQLException ex) {
            Logger.getLogger(insertDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void consultarId(int id){

        try {
            st=cc.createStatement();

            ResultSet rs = st.executeQuery( "SELECT products_id "+
                    "FROM products where products_id ="+id+";" );

            while(rs.next()){
                String idPro=rs.getString(1);
                idProd =  Integer.parseInt(idPro);
                System.out.println("este es el dato de rs.getString "+rs.getString(1) );
                System.out.println("este es el id consultado en metodo consultar id "+ idProd);
            }
//            st.close();
//            cc.close();

        } catch (SQLException ex) {
            Logger.getLogger(insertDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
