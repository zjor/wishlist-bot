<script setup>
import FaLinkIcon from "@/components/icons/FaLinkIcon.vue";
import {isValidUrl} from "@/lib/utils";

const props = defineProps({
  name: String,
  description: String,
  tags: Array[String],
  url: String
})
</script>

<template>
  <div class="item flex-row flex-grow-1">
    <div class="preview">
      <img
          src="https://res.cloudinary.com/zjor-storage/image/upload/v1684312235/wishlist_preview_vefxdb.png"
          alt="preview">
    </div>
    <div class="details flex-col flex-grow-1">
      <div class="name">{{ props.name }}</div>
      <div class="description">{{ props.description }}</div>
      <div class="tags flex-row">
        <div class="tag" v-for="tag in props.tags" :key="tag">
          #{{ tag }}
        </div>
      </div>
    </div>
    <div class="flex-col" v-if="isValidUrl(props.url)">
      <a :href="props.url">
        <FaLinkIcon class="icon"/>
      </a>
    </div>
  </div>
</template>

<style scoped>
.item {
  padding: 8px;
  position: relative;
  box-sizing: border-box;
}
.item:not(:last-child):after {
  content: "";
  display: block;
  position: absolute;
  height: 1px;
  background: #cfcfcf;
  left: 2em;
  right: 2em;
  bottom: 0;
}

.details {
  padding-right: 1em;
}

.name {
  font-weight: bolder;
}

.description {
  font-size: 0.9em;
}

.tags {
  gap: 8px;
  font-size: 0.9em;
}

.tag {
  padding: 1px 4px;
  background-color: wheat;
  border-radius: 4px;
}

.icon {
  width: 24px;
  height: 24px;
  padding: 2px;
  color: var(--vt-c-black-mute);
}

.preview {
  padding: 1em;
}

.preview img {
  width: 64px;
  height: 64px;
}

</style>
