package cz.muni.fi.web;

import cz.muni.fi.movies.Movie;
import cz.muni.fi.movies.MovieException;
import cz.muni.fi.movies.MovieManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(MoviesServlet.URL_MAPPING + "/*")
public class MoviesServlet extends HttpServlet {

    private static final String LIST_JSP = "/list.jsp";
    public static final String URL_MAPPING = "/movies";

    private final static Logger log = LoggerFactory.getLogger(MoviesServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("GET ...");
        showMoviesList(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //support non-ASCII characters in form
        request.setCharacterEncoding("utf-8");
        //action specified by pathInfo
        String action = request.getPathInfo();
        log.debug("POST ... {}",action);
        switch (action) {
            case "/add":
                //getting POST parameters from form
                String name = request.getParameter("name");
                String year = request.getParameter("year");
                String classification = request.getParameter("classification");
                String description = request.getParameter("description");
                String location = request.getParameter("location");
                //form data validity check
                if (name == null || name.length() == 0 || year == null || year.length() == 0 ||
                        classification == null || classification.length() == 0 ||
                        description == null || description.length() == 0 ||
                        location == null || location.length() == 0) {
                    request.setAttribute("chyba", "Je nutné vyplnit všechny hodnoty !");
                    log.debug("form data invalid");
                    showMoviesList(request, response);
                    return;
                }
                //form data processing - storing to database
                try {
                    Movie movie = new Movie();
                    movie.setName(name);
                    movie.setYear(Integer.parseInt(year));
                    movie.setClassification(classification);
                    movie.setDescription(description);
                    movie.setLocation(location);

                    getMovieManager().createMovie(movie);
                    //redirect-after-POST protects from multiple submission
                    log.debug("redirecting after POST");
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (MovieException e) {
                    log.error("Cannot add movie", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }
            case "/delete":
                try {
                    Long id = Long.valueOf(request.getParameter("id"));
                    getMovieManager().deleteMovie(getMovieManager().getMovie(id));
                    log.debug("redirecting after POST");
                    response.sendRedirect(request.getContextPath()+URL_MAPPING);
                    return;
                } catch (MovieException e) {
                    log.error("Cannot delete movie", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }
            case "/update":
                //TODO
                return;
            default:
                log.error("Unknown action " + action);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action " + action);
        }
    }

    /**
     * Gets MovieManager from ServletContext, where it was stored by {@link StartListener}.
     *
     * @return MovieManager instance
     */
    private MovieManager getMovieManager() {
        return (MovieManager) getServletContext().getAttribute("movieManager");
    }

    /**
     * Stores the list of movies to request attribute "movies" and forwards to the JSP to display it.
     */
    private void showMoviesList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            log.debug("showing table of movies");
            List<Movie> aha = getMovieManager().getAllMovies();
            request.setAttribute("movies", aha);
            request.getRequestDispatcher(LIST_JSP).forward(request, response);
        } catch (RuntimeException e) {
            log.error("Cannot show movies", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
