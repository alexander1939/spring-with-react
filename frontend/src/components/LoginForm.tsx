import { useState } from "react";
import { useAuth } from "../hooks/useAuth";
import { useAuthContext } from "../context/AuthContext";

const LoginForm = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const { login, loading, error } = useAuth();
  const { login: loginContext } = useAuthContext();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const success = await login({ email, password });

    if (success) {
      loginContext("TOKEN_DUMMY");
      alert("Inicio de sesión exitoso");
    }
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen">
      <h2 className="text-2xl font-bold mb-4">Iniciar Sesión</h2>
      <form onSubmit={handleSubmit} className="w-80 p-4 border rounded-lg shadow">
        <div className="mb-4">
          <label className="block text-sm font-medium">Email</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            className="w-full p-2 border rounded"
          />
        </div>
        <div className="mb-4">
          <label className="block text-sm font-medium">Contraseña</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            className="w-full p-2 border rounded"
          />
        </div>
        {error && <p className="text-red-500 text-sm">{error}</p>}
        <button
          type="submit"
          disabled={loading}
          className="w-full bg-blue-500 text-white py-2 rounded"
        >
          {loading ? "Cargando..." : "Iniciar Sesión"}
        </button>
      </form>
    </div>
  );
};

export default LoginForm;
