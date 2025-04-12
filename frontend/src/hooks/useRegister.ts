import { useState } from "react";
import axios from "axios";

interface RegisterData {
  name: string;
  lastName: string;
  email: string;
  password: string;
}

export const useRegister = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const register = async ({ name, lastName, email, password }: RegisterData) => {
    setLoading(true);
    setError(null);

    try {
      const response = await axios.post("http://localhost:8080/api/auth/register", {
        name,
        lastName,
        email,
        password,
      });

      return response.data;
    } catch (err) {
      setError("Error al registrar usuario");
      return null;
    } finally {
      setLoading(false);
    }
  };

  return { register, loading, error };
};
