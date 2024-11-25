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
        //TODO revisar los problemas.
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
        try {
            preparedStatement=mySQL.prepareStatement(sentenciQuery);
            preparedStatement.setInt(1,stock);
            ResultSet resultSet= preparedStatement.executeQuery();
            if (resultSet.next()){
                while (resultSet.next()){
                    System.out.println(resultSet.getString(1));
                }
            }else {
                System.out.println("Felicidades, no hay productos con un stock por debajo del indicado");
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
        String sentencia1="select id_almacen, nombre_almacen from almacenes";
        String sentencia2="select cantidad from almacenes_productos where id_almacen= ?";
        PreparedStatement preparedStatement1;
        PreparedStatement preparedStatement2;
        int cantidad=0;
        int cantidadTotal=0;
        try {
            preparedStatement1=posSQL.prepareStatement(sentencia1);
            ResultSet resultSet=preparedStatement1.executeQuery();
            while (resultSet.next()){
                int id=resultSet.getInt(1);
                String nombreAlmacen=resultSet.getString(2);
                preparedStatement2= posSQL.prepareStatement(sentencia2);
                preparedStatement2.setInt(1,id);
                ResultSet resultSet2=preparedStatement2.executeQuery();
                while (resultSet2.next()){
                cantidad=resultSet2.getInt(1);
                cantidadTotal=cantidadTotal+cantidad;
                }
                System.out.println("El almacen " + nombreAlmacen + " con id " + id + " tiene almaceados " + cantidadTotal + " productos en total.");
                cantidadTotal=0;
            }
        }catch (SQLException a){
            System.out.println(a.toString());
        }

    }
        }




