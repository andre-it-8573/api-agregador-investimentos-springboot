package run.example.agregador_investimentos.Security.Enum;

public enum RolesUsuario {
    ADMIN("admin"),
    USER("usuario");

    private String role;

    RolesUsuario(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
