import java.sql.*;
import java.util.Scanner;

public class Metodos {
    private static Connection posSQL = SinglettonPosSQL.getInstance();
    private static Connection mySQL = SinglettonMySQL.getInstance();

    public void crearCategoria(String nombreCategoria) throws SQLException {
        String senteciaSQL = "Insert into categorias (nombre_categoria) values (?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = posSQL.prepareStatement(senteciaSQL);
            preparedStatement.setString(1, nombreCategoria);
            int comprobacion = preparedStatement.executeUpdate();
            if (comprobacion > 0) {
                System.out.println("La categoria " + nombreCategoria + " se agrego correctamente");
            } else {
                System.out.println("Se produjo un error durante la creacion ");
            }
        } catch (SQLException e) {
            System.out.println(e.toString());

        }
    }

    public void crearNuevoProveedor(String nombreProveedor, String nif, int telefono, String email) throws SQLException {
        String sentenciaSQL = "Insert into proveedores (nombre_proveedor, contacto) values (?, ROW (?, ?, ?, ?))";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = posSQL.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, nombreProveedor);
            preparedStatement.setString(2, nombreProveedor);
            preparedStatement.setString(3, nif);
            preparedStatement.setInt(4, telefono);
            preparedStatement.setString(5, email);
            int comprobacion = preparedStatement.executeUpdate();
            if (comprobacion > 0) {
                System.out.println("El provedor fue introducido de forma exitosa");
            } else {
                System.out.println("Se produjo un error al crear el provedor");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarProveedor(int id) throws SQLException {
        String sentenciaProducto = "Delete from productos where id_proveedor = ?";
        String sentenciaAlmacen = "Delete from almacenes_productos where id_producto in (select id_producto from productos where id_proveedor = ?)";
        String senciaProveedorL = "Delete from proveedores where id_proveedor = ?";
        PreparedStatement preparedStatement;
        PreparedStatement preparedStatement1;
        PreparedStatement preparedStatement2;
        try {
            preparedStatement = posSQL.prepareStatement(sentenciaAlmacen);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement1 = posSQL.prepareStatement(sentenciaProducto);
            preparedStatement1.setInt(1, id);
            preparedStatement1.executeUpdate();
            preparedStatement2 = posSQL.prepareStatement(senciaProveedorL);
            preparedStatement2.setInt(1, id);
            preparedStatement2.executeUpdate();
            int comprobar = preparedStatement.executeUpdate();
            if (comprobar > 0) {
                System.out.println("Se elimino de forma exitosa el proveedor");
            } else {
                System.out.println("No hay ningun proveedor con dicho id");
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void crearUsuario(String nombre, String email, int anho_nacimiento) throws SQLException {
        String sentenciaSQL = "Insert into usuarios (nombre, email, ano_nacimiento) values (?, ?, ?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = mySQL.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, email);
            preparedStatement.setInt(3, anho_nacimiento);
            int comprobacion = preparedStatement.executeUpdate();
            if (comprobacion >= 1) {
                System.out.println("Datos del usario insertados correctamente");
            } else {
                System.out.println("Error en la inseccion de datos");
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void eliminarUsuario(int id) throws SQLException {
        String sentenciaPedidosProdu = "Delete from pedidos_productos where id_pedido in (select id_pedido from pedidos where id_usuario = ?)";
        String sentenciaPedidos = "Delete from pedidos where id_usuario = ?";
        String sentenciaPUsuario = "Delete from usuarios where id_usuario = ?";
        PreparedStatement preparedStatement;
        PreparedStatement preparedStatement2;
        PreparedStatement preparedStatement3;
        try {
            preparedStatement = mySQL.prepareStatement(sentenciaPedidosProdu);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement2 = mySQL.prepareStatement(sentenciaPedidos);
            preparedStatement2.setInt(1, id);
            preparedStatement3 = mySQL.prepareStatement(sentenciaPUsuario);
            preparedStatement3.setInt(1, id);
            int comproEliminacion = preparedStatement3.executeUpdate();
            if (comproEliminacion > 0) {
                System.out.println("Usuario eliminado de forma exitosa");
            } else {
                System.out.println("No hay usuarios con dicho id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void crearProducto(String nombre, Double precio, int stock, String nombre_categoria, String nif) {
        String obtenerCategoriaPG = "select id_categoria from categorias where nombre_categoria = ?";
        String obtenerProveedorPG = "select id_proveedor from proveedores where (contacto).nif = ?";
        String insertarProductoSQL = "insert into productos ( nombre_producto, precio, stock) values (?, ?, ?)";
        String insertarProductosPG = "Insert into productos (id_producto, id_proveedor, id_categoria) values (?, ?, ?)";
        PreparedStatement preparedStatementCategoria;
        PreparedStatement preparedStatementProveedor;
        PreparedStatement preparedStatementinsertarProducto;
        PreparedStatement preparedStatementinsertarProductosPG;
        int idCategoria = 0;
        int idProveedor = 0;
        int idProductoAlmacenada = 0;
        try {
            preparedStatementCategoria = posSQL.prepareStatement(obtenerCategoriaPG);
            preparedStatementCategoria.setString(1, nombre_categoria);
            ResultSet resultSet = preparedStatementCategoria.executeQuery();
            if (resultSet.next()) {
                idCategoria = resultSet.getInt("id_categoria");
            } else {
                System.out.println("No hay ninguna id con  " +nombre_categoria + " en categorias");
                return;
            }
        }catch (SQLException e) {
            System.out.println(e.toString());
            return;
        }
        try {
            preparedStatementProveedor = posSQL.prepareStatement(obtenerProveedorPG);
            preparedStatementProveedor.setString(1, nif);
            ResultSet resultSet1 = preparedStatementProveedor.executeQuery();
            if (resultSet1.next()) {
                idProveedor = resultSet1.getInt("id_proveedor");
            } else {
                System.out.println("Error no hay proveedor con dicho id " + nif + " en la base de datos");
                return;
            }
        }catch (SQLException a) {
            System.out.println(a.toString());
            return;
        }
        try {
            preparedStatementinsertarProducto = mySQL.prepareStatement(insertarProductoSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatementinsertarProducto.setString(1, nombre);
            preparedStatementinsertarProducto.setDouble(2, precio);
            preparedStatementinsertarProducto.setInt(3, stock);
            int Comprobacion = preparedStatementinsertarProducto.executeUpdate();
            if (Comprobacion > 0) {
                ResultSet resultSet2 = preparedStatementinsertarProducto.getGeneratedKeys();
                if (resultSet2.next()) {
                    idProductoAlmacenada = resultSet2.getInt(1);
                    System.out.println("Se inserto en mySQL");
                }
            }else {
                System.out.println("Error en mysql");
                return;
            }
        }catch (SQLException b) {
            System.out.println(b.toString());
            return;
        }
        try {
                preparedStatementinsertarProductosPG = posSQL.prepareStatement(insertarProductosPG);
                preparedStatementinsertarProductosPG.setInt(1, idProductoAlmacenada);
                preparedStatementinsertarProductosPG.setInt(2, idProveedor);
                preparedStatementinsertarProductosPG.setInt(3, idCategoria);
                int comprobacionPG = preparedStatementinsertarProductosPG.executeUpdate();
                if (comprobacionPG > 0) {
                    System.out.println("Se inserto correctamente en el PG ");
                } else {
                    System.out.println("Se inserto en mysql pero no en PG ");
                }
            } catch (SQLException x) {
                System.out.println(x.toString());
            }
        }

        public void eliminarProductoPorNombre(String nombre) throws SQLException {
        String obtencionID="select id_producto from productos where nombre_producto = ?";
        String sentenciaAlmacenes="delete from almacenes_productos where id_producto = ?";
        String sentenciaProductopos="delete from productos where id_producto = ?";
        String senciapedidosSQL="delete from pedidos_productos where id_producto = ?";
        String senciaProductoSQL="delete from productos where id_producto = ?";
        int claveBorrado=0;
        PreparedStatement preparedStatementID;
        PreparedStatement preparedStatementProductosSQL;
        PreparedStatement preparedStatementPedidosSQL;
        PreparedStatement preparedStatementAlmacenes;
        PreparedStatement preparedStatementProductos;
        try {
            preparedStatementID = mySQL.prepareStatement(obtencionID);
            preparedStatementID.setString(1, nombre);
            ResultSet resultSet1 = preparedStatementID.executeQuery();
            if (resultSet1.next()) {
                claveBorrado = resultSet1.getInt(1);
            } else {
                System.out.println("No hay ningun producto con dicho nombre");
            }
            preparedStatementPedidosSQL = mySQL.prepareStatement(senciapedidosSQL);
            preparedStatementPedidosSQL.setInt(1, claveBorrado);
            int borradoConfirmado3 = preparedStatementPedidosSQL.executeUpdate();
            if (borradoConfirmado3 > 0) {
                System.out.println("Borrado los pedidos");
            } else {
                System.out.println("Error en el borrado de los pedidos");
            }
                preparedStatementProductosSQL = mySQL.prepareStatement(senciaProductoSQL);
                preparedStatementProductosSQL.setInt(1, claveBorrado);
                int borradoConfirmado4 = preparedStatementProductosSQL.executeUpdate();
                if (borradoConfirmado4 > 0) {
                    System.out.println("Exito , producto borrado");
                } else {
                    System.out.println("Error en el borrado de producto en mysql");
                }
            preparedStatementAlmacenes = posSQL.prepareStatement(sentenciaAlmacenes);
            preparedStatementAlmacenes.setInt(1, claveBorrado);
            int borradoConfirmado1 = preparedStatementAlmacenes.executeUpdate();
            if (borradoConfirmado1 > 0) {
                System.out.println("Se elimino almacenes_productos relacionado al producto");
            } else {
                System.out.println("Un error con almacenes_productos no permite borrar");
            }
            preparedStatementProductos = posSQL.prepareStatement(sentenciaProductopos);
            preparedStatementProductos.setInt(1, claveBorrado);
            int borradoConfirmado2 = preparedStatementProductos.executeUpdate();
            if (borradoConfirmado2 > 0) {
                System.out.println("Borrado en productos PG");
            } else {
                System.out.println("Error en el borrado de PG");
            }
            } catch (SQLException d) {
                System.out.println(d.toString());
            }
        }

public void listarProductosBajoStock(int stock) throws SQLException {
        String sentenciQuery="select nombre_producto from productos where stock < ?";
        PreparedStatement preparedStatement;
        int contador=0;
        try {
            preparedStatement=mySQL.prepareStatement(sentenciQuery);
            preparedStatement.setInt(1,stock);
            ResultSet resultSet= preparedStatement.executeQuery();
            while (resultSet.next()){
                    System.out.println(resultSet.getString(1));
                    contador++;
                }
            if (contador==0){
                System.out.println("No hay ningun producto con stock inferior al mencionado");
            }

        }catch (SQLException a){
            System.out.println(a.toString());
        }
}

public void obtenerTotalPedidosUsuarios() throws SQLException {
        String sentencia1="select id_usuario,nombre from usuarios";
        String sentencia2="select id_pedido from pedidos where id_usuario = ?";
        PreparedStatement preparedStatement1;
        PreparedStatement preparedStatement2;
        int contador=0;
        try {
            preparedStatement1=mySQL.prepareStatement(sentencia1);
            ResultSet resultSet=preparedStatement1.executeQuery();
            while (resultSet.next()){
                int id=resultSet.getInt(1);
                String nombre=resultSet.getString(2);
            preparedStatement2= mySQL.prepareStatement(sentencia2);
            preparedStatement2.setInt(1,id);
            ResultSet resultSet2=preparedStatement2.executeQuery();
            while (resultSet2.next()){
               contador=contador+1;
            }
                System.out.println("El usuario " + nombre +" con id "+id+ " realizo los siguientes pedidos " + contador);
            contador=0;
            }
        }catch (SQLException a){
            System.out.println(a.toString());
        }

}

    public void obtenerCantidadProductosEnCadaAlmacen() throws SQLException {
        String sentencia1 = "select a.id_almacen, a.nombre_almacen, ap.cantidad " +
                "from almacenes a " +
                "left join almacenes_productos ap ON a.id_almacen = ap.id_almacen";
        PreparedStatement preparedStatement1;
        try {
            preparedStatement1=posSQL.prepareStatement(sentencia1);
            ResultSet resultSet=preparedStatement1.executeQuery();
            while (resultSet.next()){
                int id=resultSet.getInt(1);
                String nombreAlmacen=resultSet.getString(2);
                int cantidad=resultSet.getInt(3);
                System.out.println("El almacen " + nombreAlmacen + " con id " + id + " tiene almaceados " + cantidad + " productos en total.");
            }
        }catch (SQLException a){
            System.out.println(a.toString());
        }
    }

    public void listarTodosProductosConCategoriaYProveedor() throws SQLException {
        String sentenciPG="select p.id_producto, p.id_proveedor, p.id_categoria, " +
                "c.nombre_categoria, " +
                "pr.nombre_proveedor, " +
                "(pr.contacto).nombre_contacto, (pr.contacto).nif, (pr.contacto).telefono, (pr.contacto).email " +
                "from productos p " +
                "inner join categorias c on p.id_categoria = c.id_categoria " +
                "inner join proveedores pr on p.id_proveedor = pr.id_proveedor";
        String sentenciaProducto="select * from productos where id_producto = ?";
        PreparedStatement preparedStatement1;
        PreparedStatement preparedStatement2;
        int idProducto=0;
        int idProveedor;
        int idCategoria;
        String nombreProvedor, nombreContacto, nif, mail, nombreCategoria , nombreProducto;
        int telefono, stock , id ;
        double precio;
        try {
            preparedStatement1= posSQL.prepareStatement(sentenciPG);
            ResultSet resultSet1= preparedStatement1.executeQuery();
            while (resultSet1.next()){
                idProducto=resultSet1.getInt(1);
                idProveedor= resultSet1.getInt(2);
                idCategoria=resultSet1.getInt(3);
                nombreCategoria=resultSet1.getString(4);
                nombreProvedor=resultSet1.getString(5);
                nombreContacto=resultSet1.getString(6);
                nif=resultSet1.getString(7);
                telefono=resultSet1.getInt(8);
                mail=resultSet1.getString(9);
                    System.out.println("-----------------Datos----del-----Producto------------");
                    System.out.println("Nombre proveedor " + nombreProvedor + " nombre contacto " + nombreContacto + " nif " + nif + " telefono " + telefono +" mail " +mail + " categoria "  + nombreCategoria);
                     System.out.println("Id del proveedor " +idProveedor+ " Id categoria " +idCategoria);
                      preparedStatement2=mySQL.prepareStatement(sentenciaProducto);
                      preparedStatement2.setInt(1,idProducto);
                      ResultSet resultSet2= preparedStatement2.executeQuery();
                      while (resultSet2.next()){
                          id=resultSet2.getInt(1);
                          nombreProducto=resultSet2.getString(2);
                          precio=resultSet2.getDouble(3);
                          stock=resultSet2.getInt(4);
                          System.out.println("Id del producto " + id +" nombre producto " + nombreProducto + " precio " + precio + " stock " + stock);
                      }
                  }
        }catch (SQLException a){
            System.out.println(a.toString());
        }
    }
    public void obtenerUsuariosCompraronProductosCategoria(int idCategoria) throws SQLException {
        String sentenciaIDProducto="select id_producto from productos where id_categoria = ?";
        String sentenciaIDPedidos="select id_pedido from pedidos_productos where id_producto = ?";
        String sentenciaIDUusario="select id_usuario from pedidos where id_pedido = ?";
        String sentenciaUsuario="select nombre from usuarios where id_usuario = ?";
        PreparedStatement preparedStatement1;
        PreparedStatement preparedStatement2;
        PreparedStatement preparedStatement3;
        PreparedStatement preparedStatement4;
        int id1,id2,id3;
        String nombre;
        try {
            preparedStatement1= posSQL.prepareStatement(sentenciaIDProducto);
            preparedStatement1.setInt(1,idCategoria);
            ResultSet resultSet1=preparedStatement1.executeQuery();
            while (resultSet1.next()){
                id1=resultSet1.getInt(1);
                preparedStatement2= mySQL.prepareStatement(sentenciaIDPedidos);
                preparedStatement2.setInt(1,id1);
                ResultSet resultSet2=preparedStatement2.executeQuery();
                while (resultSet2.next()){
                    id2=resultSet2.getInt(1);
                    preparedStatement3= mySQL.prepareStatement(sentenciaIDUusario);
                    preparedStatement3.setInt(1,id2);
                    ResultSet resultSet3=preparedStatement3.executeQuery();
                    while (resultSet3.next()){
                        id3=resultSet3.getInt(1);
                        preparedStatement4= mySQL.prepareStatement(sentenciaUsuario);
                        preparedStatement4.setInt(1,id3);
                        ResultSet resultSet4=preparedStatement4.executeQuery();
                        while (resultSet4.next()){
                            nombre=resultSet4.getString(1);
                            System.out.println(nombre);
                        }
                    }
                }
            }
        }catch (SQLException a){
            System.out.println(a.toString());
        }
    }
        }




