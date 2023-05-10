import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useWishlistStore = defineStore('wishlist', () => {
  const publicItems = ref([])
  const privateItems = ref([])

  function setPublicItems(value) {
    publicItems.value = value || []
  }

  function setPrivateItems(value) {
    privateItems.value = value || []
  }

  return {
    publicItems, privateItems,
    setPublicItems, setPrivateItems
  }
})
