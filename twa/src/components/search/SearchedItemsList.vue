<script setup>
import log from "@/lib/logging"
import api from "@/lib/api"
import { useUserSearchStore } from "@/stores/userSearchStore"
import {onMounted} from "vue";
import SearchedItem from "@/components/search/SearchedItem.vue";
import SearchedItemDetails from "@/components/search/SearchedItemDetails.vue";

const searchStore = useUserSearchStore()

function onItemClick(item) {
  searchStore.setSelectedItem(item)
}

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
    <SearchedItemDetails v-if="searchStore.selectedItem"/>
    <div v-else>
      <div v-if="searchStore.items.loading" class="flex flex-column flex-center">Loading...</div>
      <div v-else class="flex flex-column">
        <SearchedItem
            v-for="item in searchStore.items.items"
            :key="item.id"
            :name="item.name"
            :description="item.description"
            :tags="item.tags"
            :image-url="item.imageUrl"
            :url="item.url"
            :price="item.price"
            @click="onItemClick(item)"
        />
        <div v-if="!searchStore.items.items.length" class="flex flex-column flex-center pt-10">
          <span>No wishes yet, ask <a :href="`https://t.me/${searchStore.user.username}`">{{searchStore.user.firstName}}</a> to add some</span>
        </div>
      </div>
    </div>
  </div>
</template>
