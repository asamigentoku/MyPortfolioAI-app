<!-- pages/index.vue -->
<script setup lang="ts">
definePageMeta({
  layout: "menu"
});
</script>
<template>
  <div class="py-8 px-4 max-w-4xl mx-auto">
    <h1 class="text-3xl font-bold mb-4">トップページ</h1>
    <p class="mb-6 text-lg text-gray-700">
      ここがトップページのメインコンテンツです。
    </p>
    <NuxtLink to="/about" class="inline-block px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700">
      About ページへ
    </NuxtLink>
  </div>
</template>