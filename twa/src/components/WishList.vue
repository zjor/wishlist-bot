<script setup>
import WishListItem from "@/components/WishListItem.vue"

const props = defineProps({
  items: Array
})
</script>

<template>
  <div class="list">
    <WishListItem
        v-for="item in props.items"
        :key="item.id"
        :name="item.name"
        :description="item.description"
        :tags="item.tags"
        :url="item.url"
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
