<script setup>
import {ref} from "vue"

const output = ref('n/a')

function onClick() {
  const webApp = window.Telegram?.WebApp
  try {
    if (webApp) {
      output.value = "out: " + webApp.sendData("Hello world");
    } else {
      output.value = "webApp is: " + webApp
    }
  } catch (e) {
    output.value = "error: " + e
  }
}

</script>

<template>
<v-container>
  <div>Search feature will be here</div>
  <div style="visibility: hidden">
    <v-btn @click="onClick">Send data</v-btn>
    <div>{{ output }}</div>
  </div>
</v-container>
</template>


<style scoped>

</style>
