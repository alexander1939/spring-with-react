import { useState } from "react";
import axios from "axios";

interface LoginData {
  email: string;
  password: string;
}

export const useAuth = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const login = async ({ email, password }: LoginData) => {
    setLoading(true);
    setError(null);

    try {
      const response = await axios.post("http://localhost:8080/api/auth/login", {
        email,
        password,
      });

      localStorage.setItem("token", response.data.token);
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
  };

  return { login, logout, loading, error };
};
