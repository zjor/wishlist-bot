import { ref } from 'vue'
import { defineStore } from 'pinia'
import api from "@/lib/api";

export const useWishlistStore = defineStore('wishlist', () => {
  const publicItems = ref([])
  const privateItems = ref([])

  const privateItemDetails = ref({})

  async function loadAllItems() {
    const [publicItems, privateItems] = await Promise.all([
      api.getPublicItems(),
      api.getPrivateItems()
    ])
    setPublicItems(publicItems)
    setPrivateItems(privateItems)
  }

  async function setIsPublicAndUpdate(itemId, isPublic) {
    const response = await api.setIsPublic(itemId, isPublic)
    setPublicItems(await api.getPublicItems())
    return response
  }

  async function loadPrivateItemDetails(itemId) {
    const response = await api.getPrivateItemDetails(itemId)
    privateItemDetails.value = {...{[itemId]: response}, ...privateItemDetails.value}
    return response
  }


  function setPublicItems(value) {
    publicItems.value = value || []
  }

  function setPrivateItems(value) {
    privateItems.value = value || []
  }

  return {
    publicItems, privateItems, privateItemDetails,
    setPublicItems, setPrivateItems,
    loadAllItems, setIsPublicAndUpdate, loadPrivateItemDetails
  }
})
