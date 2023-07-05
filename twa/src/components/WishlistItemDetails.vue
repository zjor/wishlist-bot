<script setup>
import {ref, onMounted} from "vue"
import api from "@/lib/api"
import {DEFAULT_IMAGE_URL, useUiStateStore} from "@/stores/uiStateStore"
import {useWishlistStore} from "@/stores/wishlistStore"
import log from "@/lib/logging";

const wishlistStore = useWishlistStore()
const uiState = useUiStateStore()
const itemId = uiState.selectedItem.id
const defaultImageUrl = ref(DEFAULT_IMAGE_URL)
const isPublic = ref(uiState.selectedItem.public)
const isPublicLoading = ref(false)

const details = ref({})
const allowedStatuses = ref({})

function onBackClick() {
  uiState.setSelectedItem(undefined)
}

async function onPrivateToggle() {
  isPublicLoading.value = true
  const item = uiState.selectedItem
  const response = await wishlistStore.setIsPublicAndUpdate(item.id, !item.public)
  uiState.setSelectedItem(response)
  isPublic.value = response.public
  isPublicLoading.value = false
}

function refresh() {
  if (details.value && details.value.allowedStatuses) {
    let acc = {}
    details.value.allowedStatuses.forEach(it => acc = {...it, ...acc})
    allowedStatuses.value = acc
  }
  wishlistStore.loadAllItems()
}

async function setItemStatus(status) {
  details.value = await api.setStatus(itemId, status)
  refresh()
}

onMounted(async () => {
  log.info(`${itemId} is mounted`)
  details.value = wishlistStore.privateItemDetails[itemId] || {}
  details.value = await wishlistStore.loadPrivateItemDetails(itemId)
  refresh()
})

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

        <v-divider/>

        <v-container v-if="details" class="actions flex-row">
          <v-btn v-if="allowedStatuses['OPEN']"
                 @click="setItemStatus('OPEN')"
                 prepend-icon="mdi-new-box" stacked>Open
          </v-btn>
          <v-btn
              v-if="allowedStatuses['IN_PROGRESS']"
              @click="setItemStatus('IN_PROGRESS')"
              prepend-icon="mdi-clock-start" stacked>Start
          </v-btn>
          <v-btn
              v-if="allowedStatuses['DONE']"
              @click="setItemStatus('DONE')"
              prepend-icon="mdi-check" stacked>Done
          </v-btn>
          <v-btn
              v-if="allowedStatuses['ARCHIVED']"
              @click="setItemStatus('ARCHIVED')"
              prepend-icon="mdi-trash-can-outline" stacked>Archive
          </v-btn>
        </v-container>

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

.actions {
  gap: 1em;
}

.actions button {
  /*min-width: 128px;*/
}

</style>
