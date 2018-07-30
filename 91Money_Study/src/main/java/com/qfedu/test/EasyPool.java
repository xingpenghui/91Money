package com.qfedu.test;

import java.sql.*;
import java.util.Enumeration;
import java.util.Vector;

/**
 *@Author feri
 *@Date Created in 2018/7/30 00:48
 */
public class EasyPool {
    private String driverPath = ""; // 数据库驱动
    private String dbUrl = ""; // 数据 URL
    private String dbUsername = ""; // 数据库用户名
    private String dbPassword = ""; // 数据库用户密码
    private int initialConnections = 10; // 连接池的初始大小
    private int incrementalConnections = 5;// 连接池自动增加的大小
    private int maxConnections = 20; // 连接池最大的大小
    private Vector connections = null;
    private static EasyPool easyPool;
    private EasyPool(String driverPath, String dbUrl, String dbUsername,
                          String dbPassword) throws Exception {
        this.driverPath = driverPath;
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        createPool();
    }
    public static synchronized EasyPool newInstance(String jdbcDriver, String dbUrl, String dbUsername,
                                                    String dbPassword){
        if(easyPool==null){
            try {
                easyPool=new EasyPool(jdbcDriver,dbUrl,dbUsername, dbPassword);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return easyPool;
    }
    private synchronized void createPool() throws Exception {
        if (connections != null) {
            return; // 如果己经创建，则返回
        }
        Class.forName(driverPath);
        // 创建保存连接的向量 , 初始时有 0 个元素
        connections = new Vector();
        createConnections(this.initialConnections);
    }
    private void createConnections(int numConnections) throws SQLException {
        // 循环创建指定数目的数据库连接
        for (int x = 0; x < numConnections; x++) {
            if (this.maxConnections > 0  && this.connections.size() >= this.maxConnections) {
                break;
            }
            try {
                connections.addElement(new PooledConnection(newConnection()));
            } catch (SQLException e) {
                throw new SQLException();
            }
        }
    }
    private Connection newConnection() throws SQLException {
        // 创建一个数据库连接
        Connection conn = DriverManager.getConnection(dbUrl, dbUsername,
                dbPassword);
        if (connections.size() == 0) {
            DatabaseMetaData metaData = conn.getMetaData();
            int driverMaxConnections = metaData.getMaxConnections();
            if (driverMaxConnections > 0   && this.maxConnections > driverMaxConnections) {
                this.maxConnections = driverMaxConnections;
            }
        }
        return conn;
    }
    public synchronized Connection getConnection() throws SQLException {
        if (connections == null) {
            return null;
        }
        Connection conn = getFreeConnection();
        while (conn == null) {
            // 等一会再试
            wait(250);
            conn = getFreeConnection();
        }
        return conn;// 返回获得的可用的连接
    }
    private Connection getFreeConnection() throws SQLException {
        // 从连接池中获得一个可用的数据库连接
        Connection conn = findFreeConnection();
        if (conn == null) {

            // 如果目前连接池中没有可用的连接
            // 创建一些连接
            createConnections(incrementalConnections);
            // 重新从池中查找是否有可用连接
            conn = findFreeConnection();
            if (conn == null) {
                // 如果创建连接后仍获得不到可用的连接，则返回 null
                return null;
            }
        }
        return conn;
    }
    private Connection findFreeConnection() throws SQLException {
        Connection conn = null;
        PooledConnection pConn = null;
        // 获得连接池向量中所有的对象
        Enumeration enumerate = connections.elements();
        while (enumerate.hasMoreElements()) {
            pConn = (PooledConnection) enumerate.nextElement();
            if (!pConn.isBusy()) {
                conn = pConn.getConnection();
                pConn.setBusy(true);
                break; // 己经找到一个可用的连接，退出
            }
        }
        return conn;// 返回找到到的可用连接
    }
    public void returnConnection(Connection conn) {
        // 确保连接池存在，如果连接没有创建（不存在），直接返回
        if (connections == null) {
            System.out.println(" 连接池不存在，无法返回此连接到连接池中 !");
            return;
        }
        PooledConnection pConn = null;
        Enumeration enumerate = connections.elements();
        while (enumerate.hasMoreElements()) {
            pConn = (PooledConnection) enumerate.nextElement();
            if (conn == pConn.getConnection()) {
                pConn.setBusy(false);
                break;
            }
        }
    }
    public synchronized void refreshConnections() throws SQLException {
        // 确保连接池己创新存在
        if (connections == null) {
            System.out.println(" 连接池不存在，无法刷新 !");
            return;
        }
        PooledConnection pConn = null;
        Enumeration enumerate = connections.elements();
        while (enumerate.hasMoreElements()) {
            // 获得一个连接对象
            pConn = (PooledConnection) enumerate.nextElement();
            // 如果对象忙则等 5 秒 ,5 秒后直接刷新
            if (pConn.isBusy()) {
                wait(5000); // 等 5 秒
            }
            // 关闭此连接，用一个新的连接代替它。
            closeConnection(pConn.getConnection());
            pConn.setConnection(newConnection());
            pConn.setBusy(false);
        }
    }
    public synchronized void closeConnectionPool() throws SQLException {
        if (connections == null) {
            return;
        }
        PooledConnection pConn = null;
        Enumeration enumerate = connections.elements();
        while (enumerate.hasMoreElements()) {
            pConn = (PooledConnection) enumerate.nextElement();
            if (pConn.isBusy()) {
                wait(5000);
            }
            closeConnection(pConn.getConnection());
            connections.removeElement(pConn);
        }
        connections = null;
    }
    private void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
        }
    }
    private void wait(int mSeconds) {
        try {
            Thread.sleep(mSeconds);
        } catch (InterruptedException e) {
        }
    }
    public int getInitialConnections() {
        return this.initialConnections;
    }
    public void setInitialConnections(int initialConnections) {
        this.initialConnections = initialConnections;
    }
    public int getIncrementalConnections() {
        return this.incrementalConnections;
    }
    public void setIncrementalConnections(int incrementalConnections) {
        this.incrementalConnections = incrementalConnections;
    }
    public int getMaxConnections() {
        return this.maxConnections;
    }
    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }
    class PooledConnection {
        Connection connection = null;// 数据库连接
        boolean busy = false; // 此连接是否正在使用的标志，默认没有正在使用
        public PooledConnection(Connection connection) {
            this.connection = connection;
        }
        public Connection getConnection() {
            return connection;
        }
        public void setConnection(Connection connection) {
            this.connection = connection;
        }
        public boolean isBusy() {
            return busy;
        }
        public void setBusy(boolean busy) {
            this.busy = busy;
        }
    }
}
