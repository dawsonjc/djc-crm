<%@ page import="com.brewery.web.model.User" %>
<%@ page import="com.brewery.web.user.SessionUser" %>
<%
    SessionUser user = (SessionUser) request.getSession(false).getAttribute(SessionUser.SESSION_USER);
    String displayName = user == null ? "John Doe" : user.username();
    String scalaJsModule = (String) request.getAttribute("scalaJsModule");
    if (scalaJsModule == null) {
        scalaJsModule = "shell";
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= title %></title>
    <script src="https://code.jquery.com/jquery-4.0.0.min.js" integrity="sha384-fgGyf7Mo7DURSOMnOy7ed+dkq5Job205Gnzu6QIg0BOHKaqt4D76Dt8VlDCzcMHV" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/dompurify@3.4.17/dist/purify.min.js" integrity="sha384-P0JNneQf81HOl9VwNXK76cQ9VMD6Nfsnepxb0cCOg8+UzEdzgPHAPV4wVQrvYQSx" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4.3.3" integrity="sha384-aJ9rL4k6lF+91guGvUFVSkpIcge7Zd9EiI4TQDLoK9kFaFJgKHgjEXVvG/qA5COj" crossorigin="anonymous"></script>
    <style type="text/tailwindcss">
        @theme {
            --color-desert-storm: #f8f9f7;
            --color-boulder: #767676;
            --color-gray-nickel: #bcbcbb;
            --color-jakarta: #362c5f;
            --color-darker-jakarta: #261f42;
            --color-lighter-scarlet-gum: #a355d2;
            --color-light-scarlet-gum: #71299c;
            --color-scarlet-gum: #44195e;
            --color-blaze-orange: #fc6601;
            --color-sandy-brown: #ef955d;
            --color-hawaiian-tan: #924518;
        }
    </style>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@7.3.1/css/all.min.css" integrity="sha384-qrALq7+6jBOZIQsNnT6xGkMDru64qD6uTlDra39xrt2SoXl4pO3FX6Roz/RpR/BS" crossorigin="anonymous">
    <script type="importmap">{"imports":{"jquery": "<%= request.getContextPath() %>/static/vendor/jquery-module.js"}}</script>
    <script type="module" src="<%= request.getContextPath() %>/static/js/<%= scalaJsModule %>.js"></script>
</head>
<body class="m-0 min-h-screen bg-[#f2f3f3] font-sans text-[#30343b]">
<header class="fixed inset-x-0 top-0 z-50 flex h-10 bg-[#1f2229] text-[#d8dadd]">
    <a href="<%= request.getContextPath() %>/" class="flex w-16 shrink-0 items-center justify-center bg-[#20b49f] text-lg font-light text-white sm:w-40 sm:justify-start sm:px-9 sm:text-xl">
        <span class="sm:hidden">JA</span><span class="hidden whitespace-nowrap sm:inline">Joli Admin</span>
    </a>
    <div class="flex min-w-0 flex-1 items-center justify-between px-3.5">
        <div class="flex items-center gap-2">
            <button type="button" class="grid h-7 w-7 place-items-center hover:bg-white/5 hover:text-white" aria-label="Toggle navigation"><i class="fa-solid fa-bars-staggered"></i></button>
            <label class="relative hidden w-24 sm:block">
                <i class="fa-solid fa-magnifying-glass absolute left-2 top-1/2 -translate-y-1/2 text-xs text-[#9da1a8]"></i>
                <input type="search" class="h-6 w-full rounded-sm border border-[#292d35] bg-[#181b22] py-0 pl-7 pr-2 text-xs text-[#dfe1e4] outline-none" placeholder="Search..." aria-label="Search">
            </label>
        </div>
        <div class="flex items-center gap-2">
            <button type="button" class="relative grid h-7 w-7 place-items-center hover:bg-white/5 hover:text-white" aria-label="Tasks"><i class="fa-solid fa-list"></i><span class="absolute -right-1 -top-1 min-w-4 scale-75 rounded-sm bg-[#20b49f] px-1 text-center text-xs leading-4 text-white">3</span></button>
            <button type="button" class="relative grid h-7 w-7 place-items-center hover:bg-white/5 hover:text-white" aria-label="Notifications"><i class="fa-solid fa-bell"></i><span class="absolute -right-1 -top-1 min-w-4 scale-75 rounded-sm bg-[#e45151] px-1 text-center text-xs leading-4 text-white">4</span></button>
            <a id="logout" href="#" class="grid h-7 w-7 place-items-center hover:bg-white/5 hover:text-white" aria-label="Log out"><i class="fa-solid fa-right-from-bracket"></i></a>
        </div>
    </div>
</header>
