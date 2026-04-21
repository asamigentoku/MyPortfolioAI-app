<script setup lang="ts">
// 1. 生成されたファイルからインポート
import { CareerControllerApi, type CareerDto, Configuration } from '../../generated/api/index'

// 2. APIの接続設定
const config = new Configuration({
  basePath: 'http://localhost:8080' // Spring BootのURL
})

// 3. APIクラスのインスタンス化
const careerApi = new CareerControllerApi(config)

// 4. データを保持するリアクティブ変数
const careers = ref<CareerDto[]>([])

// 5. 実行関数
const loadData = async () => {
  try {
    // findByUserId3 や delete4 など、生成されたメソッドが使えます
    // ここでは仮に「全てのキャリアを取得する」メソッド（findById4など）を想定
    // ※生成されたコードに合わせてメソッド名を選んでください
    const response = await careerApi.findByUserId3(1)
    console.log(response);
    // 引数にUserIdが必要な定義になっています
    careers.value = response.data
  } catch (error) {
    console.error('通信失敗:', error)
  }
}

onMounted(() => {
  loadData()
})
</script>

<template>
  <div>
    <h1>経歴リスト</h1>
    <ul>
      <li v-for="item in careers" :key="item.id">
        {{ item.careerName }}
      </li>
    </ul>
  </div>
</template>