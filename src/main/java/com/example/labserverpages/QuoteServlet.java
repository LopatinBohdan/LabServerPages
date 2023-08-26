package com.example.labserverpages;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;

@WebServlet(name = "quoteServlet", value = "/quote-servlet")
public class Quote extends HttpServlet {

    private String url;
    private String userName;
    private String password;
    private String sqlCommand;
    private ArrayList<String> quotes;
    private int select;
    @Override
    public void init() throws ServletException {
        url="jdbc:mysql://localhost:3306/quotesDB";
        userName="root";
        password="";
        sqlCommand="";
        select=0;
        quotes=new ArrayList<String>();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        try{
            Class.forName("com.mysql.jdbc.Driver");
            try(Connection conn= DriverManager.getConnection(url,userName,password)){
                Statement statement= conn.createStatement();
                sqlCommand="SELECT * FROM Quote\n" +
                        "WHERE categoryId=1";
                statement.execute(sqlCommand);

            }
            catch(Exception e){
                PrintWriter out=response.getWriter();
                out.println("<html><body>");
                out.println("<h1>"+e.getMessage()+"</h1>");
                out.println("</body></html>");
            }

        }
        catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>" + e.getMessage() + "</h1>");
            out.println("</body></html>");
        }

        getServletContext().getRequestDispatcher("/Quote/quote.jsp").forward(request,response);
    }
    @Override
    public void destroy() {}
}
