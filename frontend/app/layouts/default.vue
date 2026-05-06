<script setup lang="ts">

</script>

<!-- layouts/default.vue -->
<template>
  <div class="min-h-screen flex flex-col">
    <!-- ヘッダー（メニュー） -->
    <header class="bg-slate-800 text-white p-4 shadow">
      <nav class="flex space-x-6">
        <NuxtLink to="/" class="hover:underline">Home</NuxtLink>
        <NuxtLink to="/about" class="hover:underline">About</NuxtLink>
      </nav>
    </header>

    <!-- メインコンテンツ（ページがここに入る） -->
    <main class="flex-1 px-4 py-6">
      <slot />
    </main>

    <!-- フッター -->
    <footer class="bg-slate-100 p-4 text-center text-sm text-gray-600">
      &copy; 2026 MyApp
    </footer>
  </div>
</template>

<style scoped>

</style>