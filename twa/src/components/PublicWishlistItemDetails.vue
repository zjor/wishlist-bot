<script setup>
import {ref} from "vue"
import {DEFAULT_IMAGE_URL, useUiStateStore} from "@/stores/uiStateStore"

const uiState = useUiStateStore()
const defaultImageUrl = ref(DEFAULT_IMAGE_URL)
const item = uiState.selectedPublicItem

function onBackClick() {
  uiState.setSelectedPublicItem(undefined)
}

</script>

<template>
  <v-layout>
    <v-app-bar color="primary" density="compact">
      <template v-slot:prepend>
        <v-app-bar-nav-icon @click="onBackClick">
          <v-icon>mdi-keyboard-backspace</v-icon>
        </v-app-bar-nav-icon>
      </template>
      <v-alert-title class="text-truncate">{{ item?.name }}</v-alert-title>
      <v-spacer/>
    </v-app-bar>
    <v-main>
      <v-container>
        <div class="preview flex-row flex-center">
          <img
              :src="item?.imageUrl || defaultImageUrl"
              alt="preview">
        </div>
        <div class="pt-4 pb-4">
          {{ item?.description }}
        </div>

        <div class="tags flex-row">
          <div class="tag" v-for="tag in item?.tags" :key="tag">
            #{{ tag }}
          </div>
        </div>

      </v-container>
    </v-main>
  </v-layout>
</template>


<style scoped>
.preview {
  display: flex;
}

.preview img {
  max-height: 256px;
}

.tags {
  gap: 8px;
  font-size: 0.9em;
}

.tag {
  padding: 1px 4px;
  background-color: wheat;
  border-radius: 4px;
}

</style>
