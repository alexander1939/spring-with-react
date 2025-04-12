import { useState } from "react";
import axios from "axios";
import { useAuthContext } from "../context/AuthContext";

interface LoginData {
  email: string;
  password: string;
}

export const useAuth = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const { login: setAuthUser } = useAuthContext(); // Usa el contexto de autenticación

  const login = async ({ email, password }: LoginData) => {
    setLoading(true);
    setError(null);

    try {
      const response = await axios.post("http://localhost:8080/api/auth/login", {
        email,
        password,
      },{
        withCredentials: true,
      }
    );
      
      const { id, name, lastName, email: userEmail } = response.data;

      const user = { id, name, lastName, email: userEmail };

      localStorage.setItem("token", response.data.token);
      localStorage.setItem("user", JSON.stringify(user));

      setAuthUser(user, response.data.token); // Actualiza el contexto de autenticación
      return true;
    } catch (err) {
      setError("Credenciales incorrectas");
      return false;
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
  };

  return { login, logout, loading, error };
};
