import { ref } from 'vue'
import { defineStore } from 'pinia'
import {getPublicItems, getPrivateItems, setIsPublic} from "@/lib/api";
import {telegramId} from "@/lib/config";

export const useWishlistStore = defineStore('wishlist', () => {
  const publicItems = ref([])
  const privateItems = ref([])

  async function loadAllItems() {
    const [publicItems, privateItems] = await Promise.all([
      getPublicItems(),
      getPrivateItems(telegramId)
    ])
    setPublicItems(publicItems)
    setPrivateItems(privateItems)
  }

  async function setIsPublicAndUpdate(itemId, isPublic) {
    const response = await setIsPublic(telegramId, itemId, isPublic)
    setPublicItems(await getPublicItems())
    return response
  }


  function setPublicItems(value) {
    publicItems.value = value || []
  }

  function setPrivateItems(value) {
    privateItems.value = value || []
  }

  return {
    publicItems, privateItems,
    setPublicItems, setPrivateItems,
    loadAllItems, setIsPublicAndUpdate
  }
})
