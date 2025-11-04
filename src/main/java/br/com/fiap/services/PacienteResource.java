package br.com.fiap.services;

import br.com.fiap.beans.Paciente;
import br.com.fiap.bo.PacienteBO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/paciente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteResource {

    private final PacienteBO pacienteBO = new PacienteBO();

    // Selecionar todos
    @GET
    public Response selecionarRs() {
        try {
            List<Paciente> pacientes = pacienteBO.selecionarBo();
            return Response.ok(pacientes).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar pacientes: " + e.getMessage())
                    .build();
        }
    }

    // Inserir novo
    @POST
    public Response inserirRs(Paciente paciente, @Context UriInfo uriInfo) {
        try {
            pacienteBO.inserirBO(paciente);

            // cria o caminho do novo recurso
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(Integer.toString(paciente.getId()));

            return Response.created(builder.build())
                    .entity(paciente)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao inserir paciente: " + e.getMessage())
                    .build();
        }
    }

    // Atualizar existente
    @PUT
    @Path("{id}")
    public Response atualizarRs(@PathParam("id") int id, Paciente paciente) {
        try {
            paciente.setId(id); // garante que o ID do path seja usado
            pacienteBO.attBo(paciente);
            return Response.ok(paciente).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar paciente: " + e.getMessage())
                    .build();
        }
    }

    // Deletar por ID
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            pacienteBO.deletar(id);
            return Response.noContent().build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar paciente: " + e.getMessage())
                    .build();
        }
    }
}
