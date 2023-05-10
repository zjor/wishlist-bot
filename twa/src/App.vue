<script setup>
import {onMounted, ref} from 'vue'
import {telegramId, logConfig} from "@/lib/config";
import {getPrivateItems, getPublicItems} from "@/lib/api";
import {useWishlistStore} from "@/stores/wishlistStore";
import WishList from "@/components/WishList.vue";

const wishlistStore = useWishlistStore()
const tab = ref(null)

async function loadWishlist() {
  const [publicItems, privateItems] = await Promise.all([
    getPublicItems(),
    getPrivateItems(telegramId)
  ])
  wishlistStore.setPublicItems(publicItems)
  wishlistStore.setPrivateItems(privateItems)
}

onMounted(async () => {
  logConfig()
  await loadWishlist()
})

</script>

<template>
  <header>
    <v-tabs
        v-model="tab"
        bg-color="primary">
      <v-tab value="private_tab">Private</v-tab>
      <v-tab value="public_tab">Public</v-tab>
    </v-tabs>
  </header>
  <main>
    <v-window v-model="tab">
      <v-window-item value="private_tab">
        <WishList :items="wishlistStore.privateItems"/>
      </v-window-item>
      <v-window-item value="public_tab" reverse-transition="true">
        <WishList :items="wishlistStore.publicItems"/>
      </v-window-item>
    </v-window>
  </main>
</template>

<style scoped>

</style>
