<script setup>
import log from "@/lib/logging"
import api from "@/lib/api"
import { useUserSearchStore } from "@/stores/userSearchStore"
import {onMounted} from "vue";

const searchStore = useUserSearchStore()

onMounted(async () => {
  if (searchStore.user) {
    const items = await api.getPublicItems(searchStore.user.extId)
    searchStore.setItems(false, items)
    console.log("Loaded items: ", searchStore.items)
  } else {
    log.error("Searched user is not set")
  }

})

</script>

<template>
  <div>
    <v-progress-linear
        :active="searchStore.items.loading"
        :indeterminate="searchStore.items.loading"
        absolute
        bottom
        color="deep-purple-accent-4"
    ></v-progress-linear>
    <div v-if="searchStore.items.loading">Loading...</div>
  </div>
</template>
