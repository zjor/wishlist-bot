<script setup>
import {DEFAULT_IMAGE_URL, useUiStateStore} from "@/stores/uiStateStore"
import {ref} from "vue"
import {telegramId} from "@/lib/config";
import {setIsPublic} from "@/lib/api"

const uiState = useUiStateStore()
const defaultImageUrl = ref(DEFAULT_IMAGE_URL)
const isPublic = ref(uiState.selectedItem.public)
const isPublicLoading = ref(false)

function onBackClick() {
  uiState.setSelectedItem(undefined)
}

async function onPrivateToggle(value) {
  console.log(value)
  isPublicLoading.value = true
  const response = await setIsPublic(telegramId, uiState.selectedItem.id, !isPublic.value)
  isPublic.value = response.public
  isPublicLoading.value = false
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
      <v-alert-title class="text-truncate">{{ uiState.selectedItem?.name }}</v-alert-title>
      <v-spacer/>
    </v-app-bar>
    <v-main>
      <v-container>
        <div class="preview flex-row flex-center">
          <img
              :src="uiState.selectedItem?.imageUrl || defaultImageUrl"
              alt="preview">
        </div>
        <div class="pt-4 pb-4">
          {{ uiState.selectedItem?.description }}
        </div>

        <div class="tags flex-row">
          <div class="tag" v-for="tag in uiState.selectedItem?.tags" :key="tag">
            #{{ tag }}
          </div>
        </div>

        <div>
            <v-switch
                :model-value="isPublic"
                :label="isPublic ? 'Make private' : 'Make public'"
                :disabled="isPublicLoading"
                @update:model-value="onPrivateToggle"
            />
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
