#!/usr/bin/env node

/**
 * Cross-platform Docker network setup script
 * Works on both Windows and Linux/Unix environments
 */

const { execSync } = require('child_process');

const NETWORK_NAME = 'futbol-network';

try {
  // Check if network already exists
  const networksList = execSync('docker network ls --format "{{.Name}}"', {
    encoding: 'utf-8',
  });

  if (networksList.includes(NETWORK_NAME)) {
    console.log(`✅ ${NETWORK_NAME} network already exists`);
  } else {
    console.log(`Creating ${NETWORK_NAME} network...`);
    execSync(`docker network create --driver bridge ${NETWORK_NAME}`);
    console.log(`✅ ${NETWORK_NAME} network created successfully`);
  }
} catch (error) {
  console.error(`Error setting up Docker network: ${error.message}`);
  process.exit(1);
}
