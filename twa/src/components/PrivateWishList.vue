<script setup>
import PrivateWishListItem from "@/components/PrivateWishListItem.vue"
import {useUiStateStore} from "@/stores/uiStateStore";

const props = defineProps({
  items: Array
})

const uiStore = useUiStateStore()

function onItemClick(item) {
  uiStore.setSelectedItem(item)
}

</script>

<template>
  <div class="list">
    <PrivateWishListItem
        v-for="item in props.items"
        :key="item.id"
        @click="onItemClick(item)"
        :name="item.name"
        :description="item.description"
        :tags="item.tags"
        :image-url="item.imageUrl"
        :url="item.url"
        :price="item.price"
    />
  </div>
  <div v-if="!props.items.length" class="no-items">
    No items yet<br/><br/>
    Create with <span>/create</span> command
  </div>
</template>

<style scoped>
.list {
  display: flex;
  flex-direction: column;
}
.no-items {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.no-items span {
  background-color: #2c3e50;
  color: white;
  padding: 1px 4px;
  border-radius: 4px;
}
</style>
