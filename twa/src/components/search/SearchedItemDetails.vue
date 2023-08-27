<script setup>
import {ref, onMounted} from "vue"
import {DEFAULT_IMAGE_URL} from "@/stores/uiStateStore"
import { useUserSearchStore} from "@/stores/userSearchStore"

const searchStore = useUserSearchStore()
const item = ref(undefined)

function openUrl() {
  window.open(item.value?.url, "_blank")
}

function onBackClick() {
  searchStore.setSelectedItem(undefined)
}

onMounted(() => {
  item.value = searchStore.selectedItem
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
      <v-alert-title class="text-truncate">{{ item?.name }}</v-alert-title>
      <v-spacer/>
    </v-app-bar>
    <v-main>
      <v-container>
        <div class="flex-row pb-4">
          <div class="preview flex-row flex-center">
            <img
                :src="item?.imageUrl || DEFAULT_IMAGE_URL"
                alt="preview">
          </div>
          <div class="flex-col ml-4 flex-grow-1">
            <div class="flex-grow-1">
              {{ item?.description }}
            </div>
            <div class="flex-row">
              <v-spacer/>
              <v-btn
                  append-icon="mdi-open-in-new"
                  variant="elevated"
                  color="secondary"
                  @click="openUrl">Visit</v-btn>
            </div>
          </div>
        </div>

        <div class="tags flex-row">
          <div class="tag" v-for="tag in item?.tags" :key="tag">
            #{{ tag }}
          </div>
        </div>

        <!-- Price section -->
        <div class="flex flex-row price-section mt-6">
          <div>Price:</div>
          <div class="font-weight-bold pl-1">${{item?.price}}</div>
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

.price-section {
  background-color: #2c3e50;
  color: white;
  padding: 0.5em;
  border-radius: 0.5em;
}

</style>
