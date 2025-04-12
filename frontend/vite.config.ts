import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

export default defineConfig({
  plugins: [react(), tailwindcss()],
  server: {
    watch: {
      usePolling: true, // Forzar detección de cambios en algunos entornos
    },
    host: true, // Exponer en la red (útil si usas Docker o WSL)
    strictPort: true, // Mantener el puerto fijo
    port: 5173, // Puerto de Vite
  },
})
