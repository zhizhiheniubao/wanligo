<script setup>
import { provide } from "vue";
provide('info','万里学院钱湖校区');
</script>
<template>
  <router-view/>
</template>

<style>
html,
	body,
	div,
	span,
	h1,
	h2,
	h3,
	h4,
	h5,
	h6,
	ul,
	ol,
	li,
	p {
		margin: 0;
		padding: 0;
    
	}

	html,
	body,
	#app {
		width: 100%;
		height: 100%;
		font-family: "微软雅黑";
    padding: 0;
	}

	ul,
	ol {
		list-style: none;
	}

	a {
		text-decoration: none;
	}
</style>
