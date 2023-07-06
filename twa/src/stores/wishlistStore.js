import { ref } from 'vue'
import { defineStore } from 'pinia'
import api from "@/lib/api";

export const useWishlistStore = defineStore('wishlist', () => {
  const publicItems = ref([])
  const privateItems = ref([])
  const profile = ref({})
  const profileStats = ref({})

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

  async function loadProfile() {
    const response = await api.getMyProfile()
    profile.value = response
  }

  async function loadProfileStats() {
    const response = await api.loadMyStats()
    profileStats.value = response
  }


  function setPublicItems(value) {
    publicItems.value = value || []
  }

  function setPrivateItems(value) {
    privateItems.value = value || []
  }

  return {
    publicItems, privateItems, privateItemDetails, profile, profileStats,
    setPublicItems, setPrivateItems,
    loadAllItems, setIsPublicAndUpdate, loadPrivateItemDetails, loadProfile, loadProfileStats
  }
})
