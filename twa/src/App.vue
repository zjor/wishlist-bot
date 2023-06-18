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
  <v-layout>
    <v-window v-model="tab" class="w-100 pb-12">
      <v-window-item value="private_tab" transition="none">
        <WishList :items="wishlistStore.privateItems"/>
      </v-window-item>
      <v-window-item value="public_tab" transition="none">
        <WishList :items="wishlistStore.publicItems"/>
      </v-window-item>
      <v-window-item value="search_tab" transition="none">
        <v-container>
          Search will be there
        </v-container>
      </v-window-item>
      <v-window-item value="profile_tab" transition="none" style="background-color: fuchsia">
        <v-container>
          Profile goes here
        </v-container>
      </v-window-item>
    </v-window>

    <v-bottom-navigation grow v-model="tab" bg-color="black">
      <v-btn value="private_tab">
        <v-icon>mdi-format-list-checks</v-icon>
        My List
      </v-btn>

      <v-btn value="public_tab">
        <v-icon>mdi-earth</v-icon>
        Public
      </v-btn>

      <v-btn value="search_tab">
        <v-icon>mdi-magnify</v-icon>
        Find by name
      </v-btn>

      <v-btn value="profile_tab">
        <v-icon>mdi-account</v-icon>
        Profile
      </v-btn>
    </v-bottom-navigation>
  </v-layout>

</template>

<style scoped>

</style>
