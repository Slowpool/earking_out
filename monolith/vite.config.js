import { defineConfig } from 'vite';
import { resolve } from 'path';

export default defineConfig({
  build: {
    outDir: resolve(__dirname, 'src/main/resources/static/js/dist'),
    emptyOutDir: true,

    rollupOptions: {
      input: {
        'vkid': resolve(__dirname, 'src/main/resources/static/js/vkid.js'),
      },
      output: {
        entryFileNames: '[name].bundle.js',
        chunkFileNames: 'chunks/[name]-[hash].js',
        assetFileNames: 'assets/[name]-[hash].[ext]'
      }
    }
  }
});