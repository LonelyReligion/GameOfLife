package pl.polsl.lab4.agnieszka.tazbirek.gameoflifewebversion.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * JakartaEE10Resource file
 * @author Agnieszka Tażbirek
 * @version 1.0
 */
@Path("jakartaee10")
public class JakartaEE10Resource {
    
    @GET
    public Response ping(){
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
}
