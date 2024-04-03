package web;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Response;
import dao.ILivreDao;
import dao.LivreDaoImpl;
import lecture.Livre;


@WebServlet(name = "cs", urlPatterns = { "/controleur", "*.do" })
public class ControleurServlet extends HttpServlet {
    //private static final long serialVersionUID = 1L;

    private ILivreDao lecture;

    @Override
    public void init() throws ServletException {
        lecture = new LivreDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        if (path.equals("/index.do")) {
            request.getRequestDispatcher("livres.jsp").forward(request, response);
        } else if (path.equals("/chercher.do")) {
            String motCle = request.getParameter("motCle");
            LivreModele model = new LivreModele();
            model.setMotCle(motCle);
            List<Livre> livres = lecture.livresParMC(motCle);
            model.setLivres(livres);
            request.setAttribute("model", model);
            request.getRequestDispatcher("livres.jsp").forward(request, response);
        } else if (path.equals("/saisie.do")) {
            request.getRequestDispatcher("saisieLivre.jsp").forward(request, response);
        } else if (path.equals("/save.do") && request.getMethod().equals("POST")) {
            try {
                String titre = request.getParameter("titreLivre");
                String auteur = request.getParameter("auteur");
               double prix = Double.parseDouble(request.getParameter("prix"));
                //double prix =0.0;
                String genre = request.getParameter("genre");
                int nbpages = Integer.parseInt(request.getParameter("nbpages"));
                //int nbpages = 0;
                Livre livre = lecture.save(new Livre(titre, auteur, prix, genre, nbpages));
                request.setAttribute("livre", livre);
                request.getRequestDispatcher("confirmation.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                // Handle the case where price or nbpages are not valid numbers
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input for price or nbpages");
            }
        } else if (path.equals("/supprimer.do")) {
            Long id = Long.parseLong(request.getParameter("id"));
            lecture.deleteLivre(id);
            response.sendRedirect("chercher.do?motCle=");
        } else if (path.equals("/editer.do")) {
            Long id = Long.parseLong(request.getParameter("id"));
            Livre livre = lecture.getLivre(id);
            request.setAttribute("livre", livre);
            request.getRequestDispatcher("editerLivre.jsp").forward(request, response);
        } else if (path.equals("/update.do")) {
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                String titreLivre = request.getParameter("titreLivre");
                String auteur = request.getParameter("auteur");
                double prix = Double.parseDouble(request.getParameter("prix"));
                String genre = request.getParameter("genre");
                int nbpages = Integer.parseInt(request.getParameter("nbpages"));
                Livre l= new Livre();
                l.setIdLivre(id);
                l.setTitreLivre(titreLivre);
                l.setAuteur(auteur);
                l.setPrix(prix);
                l.setGenre(genre);
                l.setNbpages(nbpages);
                lecture.updateLivre(l);
                request.setAttribute("livre", l);
                request.getRequestDispatcher("confirmation.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                // Handle the case where price or nbpages are not valid numbers
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input for price or nbpages");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}


