<template>
  <div>
    <NuxtLayout>
      <NuxtPage />
    </NuxtLayout>
  </div>
</template>

<style>
*, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }
html { scroll-behavior: smooth; }
body {
  background: #faf8f5;
  color: #2d2820;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
::-webkit-scrollbar { width: 6px; }
::-webkit-scrollbar-track { background: #faf8f5; }
::-webkit-scrollbar-thumb { background: #e0cfc4; border-radius: 100px; }
::-webkit-scrollbar-thumb:hover { background: #c9b4a6; }
</style>
